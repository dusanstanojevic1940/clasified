package com.example.tutorial.entities;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Static {
	public static String timeSince(Date created) {
		long diff = (new Date()).getTime() - created.getTime();
		if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)>0)
			return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+" 天前";
		else if (TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)>0)
			return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)+" 小时前";
		else if (TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)>0)
			return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)+" 分前";
		else
			return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS)+" 秒前";
	}
	
	public static String timeDifference(Date f, Date s) {
		long diff = f.getTime() - s.getTime();
		
		diff = Math.abs(diff);
		
		if (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)>0)
			return (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1)+" 天";
		else if (TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)>0)
			return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)+" 小时";
		else if (TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)>0)
			return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)+" 分";
		else
			return TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS)+" 秒";
	}
}
