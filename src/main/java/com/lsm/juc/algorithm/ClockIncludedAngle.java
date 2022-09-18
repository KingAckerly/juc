package com.lsm.juc.algorithm;

/**
 * 时钟夹角
 * 给定一个时分,求出时针和分针的夹角度数
 * 比如12点30分,分针处于时钟的数字6,时针处于时钟的数字12到1的中间
 * 一个圆圈是360度,数字6代表圆圈的一半,也就是180度
 * 一个圆圈小时有12个数字,平均每个小时区间是30度
 * 30分意味着时针走了数字区间的一半,也就是15度
 * 综上所述,12点30分,时针的角度是15度,那么时针和分针的夹角度数就是180度-15度=165度
 */
public class ClockIncludedAngle {
    public static void main(String[] args) {
        System.out.println(getClockIncludedAngle(11, 15));
        System.out.println(getClockIncludedAngle(12, 30));
        System.out.println(getClockIncludedAngle(3, 50));
        System.out.println(getClockIncludedAngle(5, 28));
    }

    /**
     * 给定一个时分,求出时针和分针的夹角度数
     *
     * @param hour   小时
     * @param minute 分
     * @return
     */
    public static double getClockIncludedAngle(int hour, int minute) {
        //每个时针区间的度数
        int hourDegrees = 360 / 12;
        //每个分针区间的度数
        int minuteDegrees = 360 / 60;
        //如果是0分,那么时针和分针的夹角就是0度,因为是重合的
        if (minute == 0) {
            return 0;
        }
        //当前分针的度数 = 分针 * 每个分针区间的度数
        int currentMinuteDegrees = minute * minuteDegrees;
        //当前分针在一圈当中的度数比例
        double currentMinuteDegreesRate = (double) currentMinuteDegrees / 360;
        //当前时针在时针区间中的度数 = 每个时针区间的度数 * 当前分针在一圈当中的度数比例
        double currentHourRangeDegrees = hourDegrees * currentMinuteDegreesRate;
        //当前时针在小时上的基准度数
        double currentHourBaseDegrees;
        if (hour == 12) {
            //如果小时是12点
            //当前时针在小时上的基准度数 = 0度
            currentHourBaseDegrees = 0;
        } else {
            //如果小时不是12点
            //当前时针在小时上的基准度数 = hourDegrees * 小时
            currentHourBaseDegrees = hourDegrees * hour;
        }
        //当前时针的实际度数 = 当前时针在小时上的基准度数 + 当前时针在时针区间中的度数
        double currentHourDegrees = currentHourBaseDegrees + currentHourRangeDegrees;
        //时针和分针的夹角
        double includedAngle;
        //时针和分针的夹角 = 当前分针的度数 和 当前时针的实际度数 互减 大的减小的
        if (currentMinuteDegrees < currentHourDegrees) {
            includedAngle = currentHourDegrees - currentMinuteDegrees;
        } else {
            includedAngle = currentMinuteDegrees - currentHourDegrees;
        }
        return includedAngle;
    }
}
