package Zhenghuo.utils;

import java.util.Random;

public class Calculate {

    // 添加加法方法
    public int add(int a, int b) {
        return a + b;
    }

    // 生成一道100以内的加减法，和20以内乘除法题目的函数
    public static Object[] generateMathQuestion() {
        Random random = new Random();
        int num1, num2, answer;
        char operator;

        int operation = random.nextInt(4); // 0: +, 1: -, 2: *, 3: /

        switch (operation) {
            case 0:
                num1 = random.nextInt(100);
                num2 = random.nextInt(100);
                answer = num1 + num2;
                operator = '+';
                break;
            case 1:
                num1 = random.nextInt(100);
                num2 = random.nextInt(100);
                answer = num1 - num2;
                operator = '-';
                break;
            case 2:
                num1 = random.nextInt(20);
                num2 = random.nextInt(20) + 1; // Ensure num2 is not zero
                answer = num1 * num2;
                operator = '*';
                break;
            case 3:
                num2 = random.nextInt(20) + 1; // Ensure num2 is not zero
                int multiplier = random.nextInt(20) + 1; // Ensure multiplier is not zero
                num1 = num2 * multiplier; // Ensure num1 is a multiple of num2
                answer = num1 / num2;
                operator = '/';
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }

        return new Object[]{num1, num2, answer, operator};

    }

    // 把数组转化为题目（String）的函数
    public static String convertArrayToQuestion(Object[] questionArray) {
        if (questionArray.length != 4) {
            throw new IllegalArgumentException("Invalid question array");
        }
        int num1 = (int) questionArray[0];
        int num2 = (int) questionArray[1];
        char operator = (char) questionArray[3];
        return num1 + " " + operator + " " + num2 + " = ?";
    }
  // 创建一个给上述题目可以生成两个干扰项（用Object[]来表示）的函数，干扰项要求要聪明一些，可以根据题目的运算符号分类讨论，以让人一眼看不出来为标准
public static Object[] generateDistractors(Object[] questionArray) {
    if (questionArray.length != 4) {
        throw new IllegalArgumentException("Invalid question array");
    }
    int num1 = (int) questionArray[0];
    int num2 = (int) questionArray[1];
    char operator = (char) questionArray[3];
    int correctAnswer = (int) questionArray[2];
    Random random = new Random();

    int distractor1, distractor2;

    switch (operator) {
        case '+':
            // 生成个位数相同但十位数不同的干扰项
            int correctUnitDigit = correctAnswer % 10;
            distractor1 = (correctAnswer / 10 + random.nextInt(5) + 1) * 10 + correctUnitDigit; // 加上1到5之间的随机数
            distractor2 = (correctAnswer / 10 - random.nextInt(5) - 1) * 10 + correctUnitDigit; // 减去1到5之间的随机数
            break;
        case '-':
            // 生成个位数相同但十位数不同的干扰项
            correctUnitDigit = correctAnswer % 10;
            distractor1 = (correctAnswer / 10 + random.nextInt(5) + 1) * 10 + correctUnitDigit; // 加上1到5之间的随机数
            distractor2 = (correctAnswer / 10 - random.nextInt(5) - 1) * 10 + correctUnitDigit; // 减去1到5之间的随机数
            break;
        case '*':
            // 生成接近正确答案的干扰项
            distractor1 = correctAnswer + (num2 * (random.nextInt(3) + 1)); // 加上num2的1到3倍
            distractor2 = correctAnswer - (num2 * (random.nextInt(3) + 1)); // 减去num2的1到3倍
            break;
        case '/':
            // 生成接近正确答案的干扰项
            distractor1 = correctAnswer + random.nextInt(3) + 1; // 加上1到3之间的随机数
            distractor2 = correctAnswer - random.nextInt(3) - 1; // 减去1到3之间的随机数
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + operator);
    }

    // 确保干扰项不等于正确答案
    if (distractor1 == correctAnswer) {
        distractor1 += 10; // 确保不等于正确答案且个位数相同
    }
    if (distractor2 == correctAnswer) {
        distractor2 -= 10; // 确保不等于正确答案且个位数相同
    }

    // 确保干扰项不相等
    if (distractor1 == distractor2) {
        distractor2 += 10;
    }

    return new Object[]{distractor1, distractor2};
}



}
