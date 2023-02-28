package com.sheetcourse.mobileterminal.utils;


import com.sheetcourse.mobileterminal.model.MySubject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelUtils {
    public interface HandleResult {
        MySubject handle(String courseStr, int row, int col);
    }

    /**
     * @param path     String
     * @param startRow int 课程表（不算表头）开始行数（从1开始）
     * @param startCol int 课程表（不算表头）开始列数（从1开始）
     * @return List<Course> 返回课程列表
     * <p>
     * 只读取6行7列
     */
    public static List<MySubject> handleExcel(String path, int startRow, int startCol, HandleResult handleResult) {
        InputStream inputStream = null;
        List<MySubject> courseList = new ArrayList<>();
        //Log.d("filePath",path);
        Workbook excel = null;
        try {
            File file = new File(path);

            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                //Log.d("file","do not exist");
                return courseList;
            }
            excel = Workbook.getWorkbook(inputStream);
            Sheet rs = excel.getSheet(0);
            int rowCount = 6;
            int weight = 2;

            Range[] ranges = rs.getMergedCells();


            if (startCol + 7 - 1 > rs.getColumns() || startRow + rowCount - 1 > rs.getRows()) {
                return courseList;
            }

            startCol -= 2;//rs.getCell以零开始
            startRow -= 2;

            for (int i = 1; i <= 7; i++) {
                for (int j = 1; j <= rowCount; j++) {
                    Cell cell = rs.getCell(startCol + i, startRow + j);
                    String str = handleCell(cell.getContents());

                    int row_length = 1;
                    for (Range range : ranges) {
                        if (range.getTopLeft() == cell) {
                            row_length = range.getBottomRight().getRow() - cell.getRow() + 1;
                            break;
                        }
                    }

                    if (!str.isEmpty()) {

                        //一个格子有两个课程,分割处理
                        String[] strings = str.split("\n\n");

                        for (String s : strings) {
                            MySubject course = handleResult.handle(s, j, i);
                            if (course != null) {
                                course.setStep(weight * row_length);
                                courseList.add(course);
                            }
                        }
                    }
                }

            }
            return courseList;
        } catch (BiffException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (excel != null)
                    excel.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<MySubject> handleExcel(String path, int startRow, int startCol) {
        return handleExcel(path, startRow, startCol, (courseStr, row, col) -> {
            MySubject course = getCourseFromString(courseStr);
            if (course == null)
                return null;
            course.setDay(col);
            course.setStart(row * 2 - 1);
            return course;
        });
    }

    public static List<MySubject> handleExcel(String path) {
        return handleExcel(path, 2, 2);
    }

    /**
     * 从表格中的内容提取课程信息
     *
     * @param str String
     * @return Course
     */
    public static MySubject getCourseFromString(String str) {

        String[] contents = str.split("\n");
        if (contents.length < 4)
            return null;
        MySubject course = new MySubject();
        course.setName(contents[0]);
        course.setTeacher(contents[1]);
        course.setWeekList(getWeekListFromString(contents[2]));

        course.setWeekOfTerm(getWeekOfTermFromWeeklist(course.getWeekList()));
        course.setRoom(contents[3]);
        System.out.println(course.getWeekOfTerm());

        return course;

    }
    private static int getWeekOfTermFromWeeklist(List<Integer> weeklist){
        int weekOfTerm = 0;
        List<Boolean> list=new ArrayList<>(20);
        for(int i=0;i<list.size();i++){
            if(i==(weeklist.get(i) -1)){
                list.set(i,true);
            }else {list.set(i,false);}
        }
        for (int i = 0, len = list.size(); i < len; i++) {
            if (list.get(i)) {
                //Log.d("weekofterm",String.valueOf(i));
                weekOfTerm++;
            }
            if (i != len - 1) {//最后不移动
                weekOfTerm = weekOfTerm << 1;
            }
        }
        return weekOfTerm;
    }

    private static int getWeekOfTermFromString(String str) {
        //Log.d("excel",str);
        String[] s1 = str.split("\\[");
        String[] s11 = s1[0].split(",");

        int weekOfTerm = -1;
        for (String s : s11) {
            if (s == null || s.isEmpty())
                continue;
            if (s.contains("-")) {
                int space = 2;
                if (s1[1].equals("周]")) {
                    space = 1;
                }
                String[] s2 = s.split("-");
                if (s2.length != 2) {
                    System.out.println("error");
                    return 0;
                }
                int p = Integer.parseInt(s2[0]);
                int q = Integer.parseInt(s2[1]);

                for (int n = p; n <= q; n += space) {
                    weekOfTerm += 1 << (Config.getMaxWeekNum() - n);
                }
            } else {
                weekOfTerm += 1 << (Config.getMaxWeekNum() - Integer.parseInt(s));
            }
        }
        return weekOfTerm;
    }
    private static List<Integer> getWeekListFromString(String str) {
        String[] s1 = str.split("\\[");
        List<Integer> weeklist = new ArrayList<>();
        if(s1[0].contains(",")){
            String[] week = s1[0].split(",");
            for (String s : week) {
                if (s.contains("-")) {
                    String[] unit = s.split("-");
                    int start = Integer.parseInt(unit[0]);
                    int end = Integer.parseInt(unit[1]);
                    for (int j = start; j < end + 1; j++) {
                        weeklist.add(j);
                    }
                } else {
                    weeklist.add(Integer.parseInt(s));
                }
            }
        }else if(s1[0].contains("-")){
            String[] unit = s1[0].split("-");
            int start = Integer.parseInt(unit[0]);
            int end = Integer.parseInt(unit[1]);
            for (int j = start; j < end + 1; j++) {
                weeklist.add(j);
            }
        }else if(s1[0].length()==1) {
            weeklist.add(Integer.parseInt(s1[0]));
        }
        System.out.println(weeklist);
        return weeklist;
    }

    /**
     * 去除字符串的首尾回车和空格
     *
     * @param str
     * @return
     */
    private static String handleCell(String str) {
        str = str.replaceAll("^\n|\n$", "");//去除首尾换行符
        str = str.trim();//去除首尾空格
        return str;
    }

}
