
package com.ikmr.banbara23.yaeyama_liner_checker.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.ikmr.banbara23.yaeyama_liner_checker.Company;
import com.ikmr.banbara23.yaeyama_liner_checker.Port;
import com.ikmr.banbara23.yaeyama_liner_checker.Status;

/**
 * Created by banbara23 on 15/09/21.
 */
public class Result implements Parcelable {

    private Company company;
    private Port port;
    private Status status;

    protected Result(Parcel in) {
        company = (Company) in.readValue(Company.class.getClassLoader());
        status = (Status) in.readValue(Status.class.getClassLoader());
        port = (Port) in.readValue(Port.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(company);
        dest.writeValue(status);
        dest.writeValue(port);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Result{" +
                "company=" + company +
                ", port=" + port +
                ", status=" + status +
                '}';
    }
}
