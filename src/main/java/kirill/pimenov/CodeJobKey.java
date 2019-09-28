package kirill.pimenov;

import java.util.Objects;

public class CodeJobKey {
    private String code, job;

    CodeJobKey(String code, String job) {
        this.code = code;
        this.job = job;
    }

    @Override
    public String toString() {
        return code + job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeJobKey that = (CodeJobKey) o;
        return Objects.equals(code, that.code) &&
                Objects.equals(job, that.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, job);
    }
}
