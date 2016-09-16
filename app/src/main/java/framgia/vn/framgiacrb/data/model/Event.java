package framgia.vn.framgiacrb.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Event extends RealmObject {
    @PrimaryKey
    @SerializedName("id")
    private String mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("start_date")
    private Date mStartTime;
    @SerializedName("finish_date")
    private Date mFinishTime;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("repeat_type")
    private String mRepeatType;
    @SerializedName("repeat_every")
    private int mRepeatEvery;
    @SerializedName("end_date")
    private Date mEndDate;
    @SerializedName("exception_date")
    private Date mExceptionDate;
    @SerializedName("type")
    private int mType;
    @SerializedName("event_id")
    private int mEventId;
    @SerializedName("color_id")
    private int mColorId;
    @SerializedName("attendees")
    private RealmList<Attendee> mAttendees;
    @SerializedName("place")
    private Place mPlace;
    @SerializedName("calendar_id")
    private int mCalendarId;
    @SerializedName("all_day")
    private boolean mAllDay;
    @SerializedName("start_repeat")
    private Date mStartRepeat;
    @SerializedName("end_repeat")
    private Date mEndRepeat;
    @SerializedName("user_id")
    private int mUserId;
    @SerializedName("repeat_ons_attributes")
    private RepeatOnAttribute mRepeatOnAttribute;
    @SerializedName("parent_id")
    private String mParentId;
    @SerializedName("exception_type")
    private String mExceptionType;
    @SerializedName("exception_time")
    private Date mExceptionTime;

    public Event() {
    }

    public Event(Event event) {
        this.mId = event.getId();
        this.mTitle = event.getTitle();
        this.mDescription = event.getDescription();
        this.mStartTime = event.getStartTime();
        this.mFinishTime = event.getFinishTime();
        this.mStatus = event.getStatus();
        this.mRepeatType = event.getRepeatType();
        this.mRepeatEvery = event.getRepeatEvery();
        this.mExceptionDate = event.getExceptionDate();
        this.mType = event.getType();
        this.mEventId = event.getEventId();
        this.mColorId = event.getColorId();
        this.mPlace = (event.getPlace() == null) ? null : new Place(event.getPlace());
        this.mAttendees = (event.getAttendees() == null) ? null : Attendee.cloneListAttendee(event
            .getAttendees());
        this.mCalendarId = event.getCalendarId();
        this.mAllDay = event.isAllDay();
        this.mUserId = event.getUserId();
        this.mStartRepeat = event.getStartRepeat();
        this.mEndRepeat = event.getEndRepeat();
        this.mRepeatOnAttribute = event.getRepeatOnAttribute();
    }

    public int getColorId() {
        return mColorId;
    }

    public void setColorId(int colorId) {
        mColorId = colorId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date startTime) {
        mStartTime = startTime;
    }

    public Date getFinishTime() {
        return mFinishTime;
    }

    public void setFinishTime(Date finishTime) {
        mFinishTime = finishTime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        this.mStatus = status;
    }

    public String getRepeatType() {
        return mRepeatType;
    }

    public void setRepeatType(String repeatType) {
        mRepeatType = repeatType;
    }

    public int getRepeatEvery() {
        return mRepeatEvery;
    }

    public void setRepeatEvery(int repeatEvery) {
        mRepeatEvery = repeatEvery;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }

    public Date getExceptionDate() {
        return mExceptionDate;
    }

    public void setExceptionDate(Date exceptionDate) {
        mExceptionDate = exceptionDate;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getEventId() {
        return mEventId;
    }

    public void setEventId(int eventId) {
        mEventId = eventId;
    }

    public boolean isAllDay() {
        return mAllDay;
    }

    public void setAllDay(boolean mAllday) {
        this.mAllDay = mAllday;
    }

    public int getUserId() {
        return mUserId;
    }

    public int getCalendarId() {
        return mCalendarId;
    }

    public void setCalendarId(int calendarId) {
        mCalendarId = calendarId;
    }

    public RealmList<Attendee> getAttendees() {
        return mAttendees;
    }

    public Place getPlace() {
        return mPlace;
    }

    public Date getStartRepeat() {
        return mStartRepeat;
    }

    public void setStartRepeat(Date mStartRepeat) {
        this.mStartRepeat = mStartRepeat;
    }

    public Date getEndRepeat() {
        return mEndRepeat;
    }

    public void setEndRepeat(Date mEndRepeat) {
        this.mEndRepeat = mEndRepeat;
    }

    public RepeatOnAttribute getRepeatOnAttribute() {
        return mRepeatOnAttribute;
    }

    public void setRepeatOnAttribute(RepeatOnAttribute mRepeatOnAttribute) {
        this.mRepeatOnAttribute = mRepeatOnAttribute;
    }

    public String getParentId() {
        return mParentId;
    }

    public void setParentId(String parentId) {
        mParentId = parentId;
    }

    public String getExceptionType() {
        return mExceptionType;
    }

    public void setExceptionType(String exceptionType) {
        mExceptionType = exceptionType;
    }

    public Date getExceptionTime() {
        return mExceptionTime;
    }

    public void setExceptionTime(Date exceptionTime) {
        mExceptionTime = exceptionTime;
    }
}
