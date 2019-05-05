

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class util 
{

	public static  String get_time() {
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime());
	}

	

	public static String reduce_time_by_1_sec(String time) {
		time = time.replace(":", "&");
		String splits[] = time.split("&");
		StringBuffer temp_time = new StringBuffer();
		if(splits[2].equals("00")||splits[2].equals("0"))
		{
			int hour,min;
			int temp_sec = 59;
			if(splits[1].equals("00")||splits[1].equals("0"))
			{
				hour=Integer.parseInt(splits[0]);
				hour--;
				min = 59;
				temp_time.append(hour+":"+min+":"+temp_sec);

			}
			else
			{
				min=Integer.parseInt(splits[1]);
				min--;
				temp_time.append(splits[0]+":"+min+":"+temp_sec);
			}
		}
		else
		{
			int temp_sec = Integer.parseInt(splits[2]);
			temp_sec--;
			temp_time.append(splits[0]+":"+splits[1]+":"+temp_sec);
		}
		return temp_time.toString();
	}

	public static void main(String[] args) 
	{
		String time = "05:05:02";
		time=reduce_time_by_1_sec(time);
		System.out.println(time);
		time=reduce_time_by_1_sec(time);
		System.out.println(time);
		time=reduce_time_by_1_sec(time);
		System.out.println(time);
	}

}
