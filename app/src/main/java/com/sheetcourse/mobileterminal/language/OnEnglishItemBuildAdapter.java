package com.sheetcourse.mobileterminal.language;

import android.text.TextUtils;

import com.sheetcourse.timetableview.listener.OnItemBuildAdapter;
import com.sheetcourse.timetableview.model.Schedule;


/**
 * 英文的课程文本设置
 */
public class OnEnglishItemBuildAdapter extends OnItemBuildAdapter {
    @Override
    public String getItemText(Schedule schedule, boolean isThisWeek) {
        if (schedule == null || TextUtils.isEmpty(schedule.getName())) return "Unknow";
        if (schedule.getRoom() == null) {
            if (!isThisWeek)
                return "[Non]" + schedule.getName();
            return schedule.getName();
        }

        String r = schedule.getName() + "@" + schedule.getRoom();
        if (!isThisWeek) {
            r = "[Non]" + r;
        }
        return r;
    }
}
