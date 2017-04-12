package com.ikmr.banbara23.yaeyama_liner_checker.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ikmr.banbara23.yaeyama_liner_checker.entity.Status;

import java.util.ArrayList;

/**
 * 詳細クラス
 */
public class StatusDetail implements Parcelable {

    private String leftTitle;
    private String rightTitle;
    private ArrayList row;

    /**
     * 行
     */
    class Row {
        Column column;

        /**
         * カラム
         */
        protected class Column {
            String time;
            Status status;
            String comment;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public Status getStatus() {
                return status;
            }

            public void setStatus(Status status) {
                this.status = status;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }
        }

        public Column getColumn() {
            return column;
        }

        public void setColumn(Column column) {
            this.column = column;
        }

    }

    /**
     * ヘッダーの左タイトル文字
     *
     * @return タイトル文字
     */
    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    /**
     * ヘッダーの右タイトル文字
     *
     * @return タイトル文字
     */
    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle;
    }

    public ArrayList getRow() {
        return row;
    }

    public void setRow(ArrayList row) {
        this.row = row;
    }

    protected StatusDetail(Parcel in) {
        leftTitle = in.readString();
        rightTitle = in.readString();
        if (in.readByte() == 0x01) {
            row = new ArrayList<>();
            in.readList(row, Row.class.getClassLoader());
        } else {
            row = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(leftTitle);
        dest.writeString(rightTitle);
        if (row == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(row);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StatusDetail> CREATOR = new Parcelable.Creator<StatusDetail>() {
        @Override
        public StatusDetail createFromParcel(Parcel in) {
            return new StatusDetail(in);
        }

        @Override
        public StatusDetail[] newArray(int size) {
            return new StatusDetail[size];
        }
    };
}