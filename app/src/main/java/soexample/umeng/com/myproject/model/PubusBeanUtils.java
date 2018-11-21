package soexample.umeng.com.myproject.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import soexample.umeng.com.myproject.greendao.DaoMaster;
import soexample.umeng.com.myproject.greendao.PubusBeanDao;

public class PubusBeanUtils {

    private PubusBeanDao pubusBeanDao;

 public PubusBeanUtils() {
    }

    ;

    private static PubusBeanUtils pubusBeanUtils;

    public static PubusBeanUtils getPubusBeanUtils() {

        if (pubusBeanUtils == null) {
            pubusBeanUtils = new PubusBeanUtils();
        }
        return pubusBeanUtils;
    }

    //初始化
    public void init(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "azx");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        pubusBeanDao = daoMaster.newSession().getPubusBeanDao();
    }

    //增
    public void insert(PubusBean pubusBean) {
        pubusBeanDao.insert(pubusBean);

    }

    //查
    public List<PubusBean> query() {

        return pubusBeanDao.loadAll();
    }

    //查询单个
    public PubusBean queryOne(String key) {

        return pubusBeanDao.load(Long.parseLong(key));
    }

    //删
    public void delete() {

        pubusBeanDao.deleteAll();
    }

    //删除单个
    public void deleteOne(String key) {
        pubusBeanDao.deleteByKey(Long.parseLong(key));
    }

    //修改
    public void update(PubusBean pubusBean) {
        pubusBeanDao.update(pubusBean);

    }
}
