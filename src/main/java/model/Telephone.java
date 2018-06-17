package model;

/**
 * Created by Vult on 2018-06-17.
 */
public abstract class Telephone implements Comparable {
    private String phoneNumber;

    public Telephone(String phoneNumber) throws Exception {
        validateNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    public abstract void validateNumber(String phoneNumber) throws Exception;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Telephone telephone = (Telephone) o;

        return phoneNumber.equals(telephone.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }


    @Override
    public int compareTo(Object o) {
        return Integer.parseInt(this.phoneNumber) - Integer.parseInt(o.toString());
    }
}
