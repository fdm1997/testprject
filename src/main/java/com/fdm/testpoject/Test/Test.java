package com.fdm.testpoject.Test;

        import java.net.Inet4Address;
        import java.util.*;

/**
 * @author fdm
 * @date 2019/10/20 14:43
 * @Description:
 */
public class Test {

    public static void main(String[] args) {

       // String s = "25525511135";
        String s1 = "25525511132";
        Test test = new Test();
        //System.out.println(test.get(s));
        System.out.println("----------------");
        System.out.println(test.get(s1));
    }
    public List<String> get(String s) {
        ArrayList<String> result = new ArrayList<String>();
        int len = s.length();

        for (int i = 1; i < 4 && i < len - 2; i++) {
            for (int j = i + 1; j < i + 4 && j < len - 1; j++) {
                for (int k = j + 1; k < j + 4 && k < len; k++) {
                    if (len - k >= 4){
                        continue;
                    }
                    // 截取字符串并转换成数字
                    int a = Integer.parseInt(s.substring(0, i));
                    int b = Integer.parseInt(s.substring(i, j));
                    int c = Integer.parseInt(s.substring(j, k));
                    int d = Integer.parseInt(s.substring(k));
                    // 判断数字是否符合IP地址格式
                    if (a > 255 || b > 255 || c > 255 || d > 255){
                        continue;
                    }
                    String ip = a + "." + b + "." + c + "." + d;
                    if (ip.length() < len + 3){
                        continue;
                    }
                    result.add(ip);
                }
            }
        }
        return result;
    }

}



/**
 * 学生类
 */
class Student{
    private String number;//学号
    private String name; //姓名
    private Integer age; //年龄

    public Student(String number, String name, Integer age) {
        this.number = number;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

class StudentManger{

    static List<Student> students = new ArrayList<>();

    /**
     * 添加学生
     * @param student
     */
    public void addStudent(Student student){
        boolean flag = true;
        for (Student std:students) {
            if (std.getNumber().equals(student.getNumber())) {
                flag = false;
                break;
            }
        }

        if (flag == false){
            System.out.println("该学生已存在");
        }else {
            students.add(student);
            System.out.println("添加成功");
        }
    }

    /**
     * 删除学生信息
     */
    public void removeStudent(String number){
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getNumber().equals(number)){
                iterator.remove();
            }
        }
    }

    /**
     * 更新学生信息
     * @param student
     */
    public void updateStudents(Student student){
        for (Student std:students) {
            if (std.getNumber().equals(student.getNumber())){
                students.set(students.indexOf(std),student);
                System.out.println("修改成功");
            }
        }
    }

    /**
     * 查看所有学生信息
     * @return
     */
    public  List<Student> getStudents(){
        return students;
    }

    /**
     * 根据名字查询学生信息
     */
    public Student getStudent(String name){
       for (Student student:students){
           if (student.getName().equals(name)){
               return student;
           }
       }
       return null;
    }
}