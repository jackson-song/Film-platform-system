package com.read.read_book.recommendtest;

import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.Comment;

import java.util.*;

public class wbCF {

    // 读取所有评论数据,构建用户--评分矩阵
    public static Map<String, Object> wreadComments(List<Comment> comments) {

        Map<Integer, Integer> useridMap = new HashMap<>();
        Map<Long, Integer> bookidMap = new HashMap<>();
        Map<Integer, Integer> iduserMap = new HashMap<>();
        Map<Integer, Long> idbookMap = new HashMap<>();

        int UserNum = 0;
        int BookNum = 0;
        int RatingNum = 0;
        for(Comment comment: comments) {
            int userId = comment.getUserid();
            long bookIsbn = comment.getIsbn();

            // 未记录该用户编号
            if(!useridMap.containsKey(userId)) {
                useridMap.put(userId, UserNum);
                iduserMap.put(UserNum, userId);
                UserNum ++;
            }
            // 未记录该书籍编号
            if(!bookidMap.containsKey(bookIsbn)) {
                bookidMap.put(bookIsbn, BookNum);
                idbookMap.put(BookNum, bookIsbn);
                BookNum ++;
            }
            RatingNum ++;
        }

//        System.out.println(useridMap);
//        System.out.println(bookidMap);

        // 评分矩阵
        double[][] ratingsM = new double[UserNum][BookNum];

        // 初始化 全部为0.0
        for (int i = 0; i < UserNum; i ++ ) {
            Arrays.fill(ratingsM[i], 0.0);
        }

        for (Comment comment : comments) {
            int userId = comment.getUserid();
            long bookIsbn = comment.getIsbn();
            int row = useridMap.get(userId);
            int col = bookidMap.get(bookIsbn);
            ratingsM[row][col] = comment.getRate();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("ratingsM", ratingsM);
        result.put("useridMap", useridMap);
        result.put("bookidMap", bookidMap);
        result.put("UserNum", UserNum);
        result.put("BookNum", BookNum);
        result.put("RatingNum", RatingNum);
        result.put("iduserMap", iduserMap);
        result.put("idbookMap", idbookMap);

        System.out.println(useridMap);
        System.out.println(bookidMap);
        System.out.println(iduserMap);
        System.out.println(idbookMap);
        return result;
    }

    public static double[][] computeSM(double[][] ratingsM) {
        int UserNum = ratingsM.length;
        double[][] sM = new double[UserNum][UserNum];

        for (int i = 0; i < UserNum; i ++ ) {
            sM[i][i] = 1.0;
            for (int j = i + 1; j < UserNum; j ++ ){
                double dotProduct = 0.0;
                double normI = 0.0, normJ = 0.0;
                for (int k = 0; k < ratingsM.length; k ++ ) {
                    if(ratingsM[i][k] != 0.0 && ratingsM[j][k] != 0.0) {
                        dotProduct += ratingsM[i][k] * ratingsM[j][k];
                    }
                    normI += ratingsM[i][k] * ratingsM[i][k];
                    normJ += ratingsM[j][k] * ratingsM[j][k];
                }
                if (normI > 0 && normJ > 0) {
                    sM[i][j] = dotProduct / Math.sqrt(normI * normJ);
                    sM[j][i] = sM[i][j];
                }
            }
        }
        return sM;
    }

    public static List<Long> wrecommendBooks(List<Comment> comments, int userId, int numRecs) {

        Map<String, Object> data = wreadComments(comments);
//        System.out.println(data.get("ratingsM"));
        // 用户--评分 矩阵
        double[][] ratingMatrix = (double[][]) data.get("ratingsM");
        System.out.println("用户评分矩阵:");
        for(int i = 0; i < ratingMatrix.length; i ++ ) {
            for(int j = 0; j < ratingMatrix[i].length; j ++ ) {
                System.out.print(ratingMatrix[i][j] + " ");
            }
            System.out.println();
        }

        // 用户--用户相似度矩阵
        double[][] sM = computeSM(ratingMatrix);
        System.out.println("相似度矩阵:");
        for(int i = 0; i < sM.length; i ++ ) {
            for(int j = 0; j < sM[i].length; j ++ ) {
                System.out.print(sM[i][j] + " ");
            }
            System.out.println();
        }

        //用户序号->userid,书籍序号->isbn
        Map<Integer, Integer> useridMap = (HashMap<Integer, Integer>) data.get("useridMap");
        Map<Long, Integer> bookidMap = (HashMap<Long, Integer>) data.get("bookidMap");
        // 用户数和书本数
        int userNum = (int) data.get("UserNum");
        int bookNum = (int)data.get("BookNum");

        // 用户原始评分表
        int row = useridMap.get(userId);
        double[] userRatings = new double[bookNum];
        for (int i = 0; i < bookNum; i ++ ) {
            userRatings[i] = ratingMatrix[row][i];
        }

        // 预测用户的评分
        double[] preRatings = new double[bookNum];
        double sumwr = 0.0, sumw = 0.0;
        for(int j = 0; j < bookNum; j ++ ) {
            if(userRatings[j] == 0) {
                for(int i = 0; i < userNum; i ++ ) {
                    if(i == row) continue;
                    if(ratingMatrix[i][j] > 0 && sM[row][i] > 0) {
                        sumwr += ratingMatrix[i][j] * sM[row][i];
                        sumw += sM[row][i];
                    }
                }
                preRatings[j] = sumwr / sumw;
            } else {
                preRatings[j] = 0.0;
            }
        }


        System.out.println("最终预测评分:");
        for (int i = 0; i < bookNum; i ++ ) System.out.print(preRatings[i] + " ");

        Map<Integer, Long> idBookMap = (HashMap<Integer, Long>)data.get("idbookMap");
        System.out.println("接到的idbookmap:");
        System.out.println(idBookMap);

        List<Long> recommendres = new ArrayList<>();
        while (numRecs > 0) { //
            int highestIndex = 0;
            for (int i = 1; i < preRatings.length; i++) {
//                System.out.println((!Double.isNaN(preRatings[i])) + " " + (preRatings[i] > preRatings[highestIndex]));
//                System.out.println(preRatings[i] + " " + preRatings[highestIndex]);

                if (preRatings[i] > 0 && preRatings[i] > preRatings[highestIndex]) {
                    highestIndex = i;
                }
            }
            if (preRatings[highestIndex] > 0.0) {
//                System.out.println(highestIndex);
//                System.out.println(idBookMap.get("highestIndex"));
//                Long tmp = idBookMap.get(highestIndex)
                recommendres.add(idBookMap.get(highestIndex));
            }

            preRatings[highestIndex] = 0.0;
            numRecs--;
        }
        System.out.println(recommendres.size());
        for (Long l : recommendres) System.out.println(l);

        //返回基于用户协同推荐的书籍isbn列表(可能为空)
        return recommendres;
    }
}
