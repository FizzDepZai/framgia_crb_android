package framgia.vn.framgiacrb.data.local;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import framgia.vn.framgiacrb.asyntask.RegisterNotificationAsyncTask;
import framgia.vn.framgiacrb.data.model.EventRepository;
import framgia.vn.framgiacrb.listener.OnLoadEventListener;
import framgia.vn.framgiacrb.data.model.Calendar;
import framgia.vn.framgiacrb.data.model.Event;
import framgia.vn.framgiacrb.data.model.Place;
import framgia.vn.framgiacrb.data.model.User;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by nghicv on 26/07/2016.
 */
public class EventRepositoriesLocal implements EventRepository {
    public static final String START_DATE_FIELD = "mStartTime";
    public static final String ID_FIELD = "mId";
    public static final String EVENT_ID_FIELD = "mEventId";
    public static final String CALENDAR_ID_FIELD = "mId";
    public static final String PLACE_ID = "mId";
    public static final String PLACE_NAME = "mName";
    public static final String USER_ID = "mId";
    public static final String START_REPEAT = "mStartRepeat";
    public static final String END_REPEAT = "mEndRepeat";
    public static final String PARENT_ID = "mParentId";
    private Realm mRealm;

    public EventRepositoriesLocal(Realm realm) {
        mRealm = realm;
    }

    @Override
    public void insertEvent(Event event) {
    }

    public void addEvents(final List<Event> events, final OnLoadEventListener onLoadEventListener) {
        final List<Event> realmEvents = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            if (!isExists(events.get(i))) {
                realmEvents.add(events.get(i));
            }
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmEvents);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RegisterNotificationAsyncTask registerNotificationAsynTask
                    = new RegisterNotificationAsyncTask(true);
                registerNotificationAsynTask.execute(realmEvents);
                if (onLoadEventListener != null) {
                    onLoadEventListener.onSuccess();
                }
            }
        });
    }

    public void addCalendars(final List<Calendar> calendars,
                             final Realm.Transaction.OnSuccess onSuccess) {
        final List<Calendar> realmCalendars = new ArrayList<>();
        for (Calendar calendar : calendars) {
            if (!hasCalendar(calendar)) {
                realmCalendars.add(calendar);
            } else {
            }
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(realmCalendars);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onSuccess != null) {
                    onSuccess.onSuccess();
                }
            }
        });
    }

    public void addPlaces(final List<Place> places, final Realm.Transaction.OnSuccess onSuccess) {
        final List<Place> realmPlaces = new ArrayList<>();
        for (Place place : places) {
            if (!hasPlace(place)) {
                realmPlaces.add(place);
            } else {
            }
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(realmPlaces);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onSuccess != null) {
                    onSuccess.onSuccess();
                }
            }
        });
    }

    public void addUser(final List<User> users, final Realm.Transaction.OnSuccess onSuccess) {
        final List<User> realmUsers = new ArrayList<>();
        for (User user : users) {
            if (!hasUser(user)) {
                realmUsers.add(user);
            } else {
            }
        }
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(realmUsers);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onSuccess != null) {
                    onSuccess.onSuccess();
                }
            }
        });
    }

    public RealmResults<Calendar> getAllCalendars() {
        return mRealm.where(Calendar.class).findAll();
    }

    public RealmResults<Place> getAllPlaces() {
        return mRealm.where(Place.class).findAll();
    }

    public RealmResults<User> getAllUsers() {
        return mRealm.where(User.class).findAll();
    }

    public void clearCalendarFromDatabase(final Realm.Transaction.OnSuccess onSuccess) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Calendar.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onSuccess.onSuccess();
            }
        });
    }

    public void clearPlaceFromDatabase(final Realm.Transaction.OnSuccess onSuccess) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Place.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onSuccess.onSuccess();
            }
        });
    }

    public void clearDatabase(final Realm.Transaction.OnSuccess onSuccess) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (onSuccess != null)
                    onSuccess.onSuccess();
            }
        });
    }

    @Override
    public void deleteEvent(Event event) {
    }

    @Override
    public void updateEvent(Event event) {
    }

    @Override
    public RealmResults<Event> getAllEvents(int userId) {
        return mRealm.where(Event.class).findAll();
    }

    @Override
    public void getEventsByCalendar(String token, Calendar calendar, Context context) {
    }

    @Override
    public Event getEventById(int id) {
        return mRealm.where(Event.class).findFirst();
    }

    @Override
    public RealmResults<Event> getEventByDate(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.HOUR, 23);
        Date toDate = calendar.getTime();
        return mRealm.where(Event.class).between(START_DATE_FIELD, date, toDate)
            .findAllSorted(START_DATE_FIELD, Sort.ASCENDING);
    }

    public boolean isExists(Event event) {
        return mRealm.where(Event.class).equalTo(EVENT_ID_FIELD, event.getEventId())
            .equalTo(START_DATE_FIELD, event.getStartTime()).count() != 0;
    }

    public boolean hasCalendar(Calendar calendar) {
        return mRealm.where(Calendar.class).equalTo(CALENDAR_ID_FIELD, calendar.getId()).count() !=
            0;
    }

    public boolean hasPlace(Place place) {
        return mRealm.where(Place.class).equalTo(PLACE_ID, place.getId()).count() != 0;
    }

    public boolean hasUser(User user) {
        return mRealm.where(User.class).equalTo(USER_ID, user.getId()).count() != 0;
    }

    public List<Event> getAllEventRepeatByDate(Date date) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(java.util.Calendar.HOUR, 23);
        Date toDate = calendar.getTime();
        return mRealm.where(Event.class)
            .lessThan(START_REPEAT, toDate)
            .greaterThan(END_REPEAT, date)
            .findAll();
    }

    public List<Event> getEventByParentId(String parentId) {
        return mRealm.where(Event.class)
            .equalTo(PARENT_ID, parentId).findAll();
    }

    public int getSize() {
        return mRealm.where(Event.class).findAll().size();
    }
}
