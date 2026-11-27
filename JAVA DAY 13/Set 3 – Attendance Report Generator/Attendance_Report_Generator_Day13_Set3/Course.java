import java.util.Objects;

public class Course {
    private final String courseCode;
    private final String title;

    public Course(String courseCode, String title) {
        if (courseCode == null || courseCode.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be blank.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Course title cannot be blank.");
        }
        this.courseCode = courseCode.strip();
        this.title = title.strip();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course other)) {
            return false;
        }
        return Objects.equals(this.courseCode, other.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.courseCode);
    }

    @Override
    public String toString() {
        return courseCode + " (" + title + ")";
    }
}
