package com.read.read_book.recommendtest;

import com.read.read_book.Mapper.CommentMapper;
import com.read.read_book.ReadBookApplication;
import com.read.read_book.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import java.util.*;

public class CollaborativeFiltering {

    // 读入评论数据并返回评分矩阵和 ID 映射
    // 需要从数据库中取回所有comment使用list存储
    public static Map<String, Object> readComments(List<Comment> comments) {
        // 记录每个用户和书籍在矩阵中的行号和列号
        Map<Integer, Integer> userMap = new HashMap<>();
        Map<Long, Integer> bookMap = new HashMap<>();

        // 统计用户数量、书籍数量和评论数量
        int nUsers = 0;
        int nBooks = 0;
        int nRatings = 0;
        for (Comment comment : comments) {
            int userId = comment.getUserid();
            long bookId = comment.getIsbn();
            if (!userMap.containsKey(userId)) {
                userMap.put(userId, nUsers++);
            }
            if (!bookMap.containsKey(bookId)) {
                bookMap.put(bookId, nBooks++);
            }
            nRatings++;
        }

        System.out.println(userMap);
        System.out.println(bookMap);
        // 构建评分矩阵
        double[][] ratingMatrix = new double[nUsers][nBooks];
        // 初始化
        for (int i = 0; i < nUsers; i++) {
            Arrays.fill(ratingMatrix[i], Double.NaN);
        }

        // userid和isbn获取行和列 再对矩阵赋值
        for (Comment comment : comments) {
            int userId = comment.getUserid();
            long bookId = comment.getIsbn();
            int row = userMap.get(userId);
            int col = bookMap.get(bookId);
            ratingMatrix[row][col] = comment.getRate();
        }

        // 将信息存储到结果中
        Map<String, Object> result = new HashMap<>();
        result.put("ratingMatrix", ratingMatrix);
        result.put("userMap", userMap);
        result.put("bookMap", bookMap);
        result.put("nUsers", nUsers);
        result.put("nBooks", nBooks);
        result.put("nRatings", nRatings);
        return result;
    }

    // 计算余弦相似度矩阵
    public static double[][] computeSimilarityMatrix(double[][] ratingMatrix) {
        int nUsers = ratingMatrix.length;
        double[][] similarityMatrix = new double[nUsers][nUsers];

        for (int i = 0; i < nUsers; i++) {
            similarityMatrix[i][i] = 1.0;
            for (int j = i + 1; j < nUsers; j++) {
                double dotProduct = 0.0;
                double normI = 0.0, normJ = 0.0;
                for (int k = 0; k < ratingMatrix[i].length; k++) {
                    if (!Double.isNaN(ratingMatrix[i][k]) && !Double.isNaN(ratingMatrix[j][k])) {
                        System.out.println(i + j + "doukanle" + k);
                        dotProduct += ratingMatrix[i][k] * ratingMatrix[j][k];
                        normI += ratingMatrix[i][k] * ratingMatrix[i][k];
                        normJ += ratingMatrix[j][k] * ratingMatrix[j][k];
                        System.out.println("内积:" + dotProduct + " normi:" + normI + " normj:" + normJ);
                    }
                }
                if (normI > 0 && normJ > 0) {
                    similarityMatrix[i][j] = dotProduct / Math.sqrt(normI * normJ);
                    similarityMatrix[j][i] = similarityMatrix[i][j];
                }
            }
        }

        // 输出用户相似度
        for(int i = 0; i < similarityMatrix.length; i ++ ) {
            for(int j = 0; j < similarityMatrix[i].length; j ++ ) {
                System.out.print(similarityMatrix[i][j] + " ");
            }
            System.out.println();
        }
        return similarityMatrix;
    }

    // 根据已有的评论数据推荐书籍
    public static List<Integer> recommendBooks(List<Comment> comments, int userId, int numRecs) {

        System.out.println("正在读评论数据");
        // 读入评论数据
        Map<String, Object> data = readComments(comments);
        double[][] ratingMatrix = (double[][]) data.get("ratingMatrix");

        System.out.println("各用户的书籍评分");
        for(int i = 0; i < ratingMatrix.length; i ++) {
            for(int j =0; j < ratingMatrix[i].length; j ++ ) {
                System.out.print(ratingMatrix[i][j] + " ");
            }
            System.out.println();
        }

        // 从读取评论数据的结果中取出数据 userMap bookMap 是用户和图书的id -> row/col
        Map<Integer, Integer> userMap = (HashMap<Integer, Integer>) data.get("userMap");
        Map<Integer, Integer> bookMap = (HashMap<Integer, Integer>) data.get("bookMap");
        int nUsers = (int) data.get("nUsers");
        int nBooks = (int) data.get("nBooks");

        // 将当前用户的评分放入userRatings中
        int row = userMap.get(userId);
        double[] userRatings = new double[nBooks];// ratingMatrix[row];
        for (int i = 0; i < userRatings.length; i++) {
            userRatings[i] = ratingMatrix[row][i];
            if (Double.isNaN(userRatings[i])) {
                userRatings[i] = 0.0;
            }
            System.out.print(ratingMatrix[row][i] + " ");
        }

        System.out.println(userId + "用户的所有书籍评分:");
        for(int i = 0; i < userRatings.length; i ++ ) {
            System.out.print(userRatings[i] + " ");
        }

        System.out.println("\n评分矩阵构造完成");
//        System.out.println(userRatings);

        System.out.println("计算用户相似度....");
        // 计算用户相似度
        double[][] similarityMatrix = computeSimilarityMatrix(ratingMatrix);
        System.out.println("相似度计算完成\n 调用结束后的ratingMAtrix:\n");

        for(int i = 0; i < ratingMatrix.length; i ++ ) {
            for(int j = 0; j < ratingMatrix[i].length; j ++ ) {
                System.out.print(ratingMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("预测评分....");
        // 预测评分
        double[] predictedRatings = new double[nBooks];

        for(int j = 0; j  < ratingMatrix[row].length; j ++ ) {
            System.out.print(ratingMatrix[row][j] + " ");
        }

        for (int j = 0; j < ratingMatrix[row].length; j++) {
            if (ratingMatrix[row][j] == 0) {
                System.out.println(j + "书没看");
            }
            if (Double.isNaN(ratingMatrix[row][j])) {
                System.out.println(j + "书没看");
                double numerator = 0.0, denominator = 0.0;
                for (int i = 0; i < similarityMatrix[row].length; i++) {
                    System.out.println((i != row) + " " + (!Double.isNaN(ratingMatrix[i][j])) + " " + (similarityMatrix[row][i]));
                    if (i != row && !Double.isNaN(ratingMatrix[i][j]) && similarityMatrix[row][i] > 0) {
                        numerator += similarityMatrix[row][i] * ratingMatrix[i][j];
                        denominator += similarityMatrix[row][i];
                        System.out.println(numerator + " " + denominator);
                    }
                }
                predictedRatings[j] = numerator / denominator;
            } else {
                predictedRatings[j] = 0.0;
            }
        }

        //预测结果
        System.out.println("预测结果:");
        for(int i = 0; i < predictedRatings.length; i ++ ) {
            System.out.print(predictedRatings[i] + " ");
        }



        // 选取评分最高的若干本书籍
        List<Integer> recommendedBooks = new ArrayList<>();
        while (recommendedBooks.size() < numRecs) {
            System.out.println("在选书....");
            int highestIndex = 0;
            for (int i = 1; i < predictedRatings.length; i++) {
                System.out.println((!Double.isNaN(predictedRatings[i])) + " " + (predictedRatings[i] > predictedRatings[highestIndex]));
                System.out.println(predictedRatings[i] + " " + predictedRatings[highestIndex]);
                if (!Double.isNaN(predictedRatings[i]) && predictedRatings[i] > predictedRatings[highestIndex]) {
                    highestIndex = i;
                }
            }
            if (predictedRatings[highestIndex] > 0.0) {
                recommendedBooks.add(highestIndex);
            }
            predictedRatings[highestIndex] = Double.NaN;

            System.out.println(highestIndex);

            numRecs--;
        }

        return recommendedBooks;
    }

    public static void main(String[] args) {
        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
        collaborativeFiltering.solve();
    }

    public void solve() {
//        List<Comment> allcomments = commentMapper.selectList(null);
//        System.out.println(recommendBooks(allcomments, 1, 1));
    }
}
