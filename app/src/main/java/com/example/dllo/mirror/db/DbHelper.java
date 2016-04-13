package com.example.dllo.mirror.db;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.dllo.mirror.baseworks.BaseApplication;

import java.util.List;

/**
 * Created by dllo on 16/4/13.
 */
public class DbHelper {
    private static final String TAG = DbHelper.class.getSimpleName();
    private static DbHelper instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private HomeDataDao userDao;


    /**
     * 采用单例模式
     *
     * @param context 上下文
     * @return dbservice
     */
    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper();
                    if (appContext == null) {
                        appContext = context.getApplicationContext();
                    }
                    instance.mDaoSession = BaseApplication.getDaoSession(context);
                    instance.userDao = instance.mDaoSession.getHomeDataDao();
                }
            }
        }

        return instance;
    }

    /**
     * 根据用户id,取出用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    public HomeData loadNote(long id) {
        if (!TextUtils.isEmpty(id + "")) {
            return userDao.load(id);
        }
        return null;
    }

    /**
     * 取出所有数据
     *
     * @return 所有数据信息
     */
    public List<HomeData> loadAllNote() {
        return userDao.loadAll();
    }

    /**
     * 生成按id倒排序的列表
     *
     * @return 倒排数据
     */
    public List<HomeData> loadAllNoteByOrder() {
        return userDao.queryBuilder().orderDesc(HomeDataDao.Properties.Id).list();
    }

    /**
     * 根据查询条件,返回数据列表
     *
     * @param where  条件
     * @param params 参数
     * @return 数据列表
     */
    public List<HomeData> queryNote(String where, String... params) {
        return userDao.queryRaw(where, params);
    }


    public HomeData getNote(String key) {
        try{
            HomeData data = (HomeData) userDao.queryBuilder()
                    .where(HomeDataDao.Properties.Key.eq(key)).uniqueOrThrow();
            return data;
        }catch (Exception o){
            Toast.makeText(appContext, "网络状态异常", Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    /**
     * 根据用户信息,插件或修改信息
     *
     * @param user 用户信息
     * @return 插件或修改的用户id
     */
    public long saveNote(HomeData user) {
        return userDao.insertOrReplace(user);
    }


    /**
     * 批量插入或修改用户信息
     *
     * @param list 用户信息列表
     */
    public void saveNoteLists(final List<HomeData> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        userDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    HomeData user = list.get(i);
                    userDao.insertOrReplace(user);
                }
            }
        });

    }

    /**
     * 删除所有数据
     */
    public void deleteAllNote() {
        userDao.deleteAll();
    }

    /**
     * 根据id,删除数据
     *
     * @param id 用户id
     */
    public void deleteNote(long id) {
        userDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    /**
     * 根据用户类,删除信息
     *
     * @param user 用户信息类
     */
    public void deleteNote(HomeData user) {
        userDao.delete(user);
    }

    //    删除某一个数据
    public void deleteData(String data) {
//        Log.e("删除了哪一个啊?", data);
//        List<HomeData> sc = userDao.loadAll();
//        for (int i = 0; i < sc.size(); i++) {
//            if (sc.get(i).get.equals(data)) {
//                userDao.delete(sc.get(i));
//            }
//        }
    }

    //    添加某一个数据
    public void addData(HomeData homeData) {
        for (int i = 0; i < loadAllNote().size(); i++) {
            if (loadAllNote().get(i).getKey().equals(homeData.getKey())) {
                userDao.delete(loadAllNote().get(i));
            }
        }
        userDao.insert(homeData);
    }


}
