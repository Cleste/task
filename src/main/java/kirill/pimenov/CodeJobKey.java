package kirill.pimenov;

import java.util.Objects;

/**
 * Stores a natural key that contains department's code and job.
 */
public class CodeJobKey {
    /**
     * Code and job of this key.
     */
    private String code, job;

    /**
     * Gets the code field of this key.
     *      * @return this key's code field.
     */
    String getCode() {
        return code;
    }

    /**
     * Gets the job field of this key.
     * @return this key's job field.
     */
    String getJob() {
        return job;
    }

    /**
     * Creates a new key with code and job fields.
     * @param code field of this key
     * @param job field of this key
     */
    CodeJobKey(String code, String job) {
        this.code = code;
        this.job = job;
    }

    @Override
    public String toString() {
        return "code: " + code + ", job: " + job;
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
