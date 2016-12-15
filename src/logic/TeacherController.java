package logic;

import shared.LectureDTO;
import shared.ReviewDTO;
import shared.TeacherDTO;

public class TeacherController extends UserController {

    private TeacherDTO currentTeacher;

    public TeacherController() {
        super();
    }

    public void loadTeacher(TeacherDTO currentTeacher) {
        this.currentTeacher = currentTeacher;
    }

    public double calculateAverageRatingOnLecture(int lectureId) {
        //DecimalFormat df = new DecimalFormat("#.00");
        getReviews(lectureId);

        int numberOfReviews = getReviews(lectureId).size();
        int sumOfRatings = 0;

        for (ReviewDTO review : getReviews(lectureId)) {
            sumOfRatings = sumOfRatings + review.getRating();
        }

        if (numberOfReviews == 0){
            return 0;
        }

        double average = sumOfRatings / numberOfReviews;

        return average;
    }

    public double calculateAverageRatingOnCourse(String course) {

        int lectureId = 0;
        double sumOfRatings = 0;
        double numberOfReviews = 0;

        for (LectureDTO lecture : getLectures(course)) {
            lectureId = lecture.getId();
            numberOfReviews += getReviews(lectureId).size();

            for (ReviewDTO review : getReviews(lectureId)) {
                sumOfRatings = sumOfRatings + review.getRating();
            }
        }

        double average = sumOfRatings / numberOfReviews;

        return average;
    }
}