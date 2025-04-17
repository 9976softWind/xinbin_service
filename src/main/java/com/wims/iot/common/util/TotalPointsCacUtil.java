package com.wims.iot.common.util;

import java.util.List;

/**
 * @author liwei
 * @date 2025.4.17
 */
public class TotalPointsCacUtil {

    public static double cacTotalPoints(List<Integer> pointsList){
        if (pointsList == null || pointsList.isEmpty()) {
            return 0.0;
        }

        long sum = 0;
        for (int num : pointsList) {
            sum += num;
        }

        return (double) sum / pointsList.size();
    }
}
