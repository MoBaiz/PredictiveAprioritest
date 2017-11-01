import weka.core.Instances;
import weka.experiment.InstanceQuery;

/**
 * Created by Zhengchenghao on 2017/10/31.
 */
public class DB {
    private String url;
    private String user;
    private String pwd;
    public DB(String url,String user,String pwd){
        this.url=url;
        this.user=user;
        this.pwd=pwd;
    }
    public InstanceQuery getQuery() {
        InstanceQuery query = null;
        try {
            query = new InstanceQuery();
            //添加数据库连接地址，包括数据库名称，如jdbc:mysql://localhost:3306/hospital
            query.setDatabaseURL(url);
            //添加数据库登录用户名
            query.setUsername(user);
            //添加数据库登录密码
            query.setPassword(pwd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return query;
    }
    //在MySQL中执行SQL语句，并返回一个记录查询结果的Instances对象
    public Instances getData(String sql) {
        InstanceQuery query=getQuery();
        Instances data = null;
        //添加需要执行查询的SQL语句
        query.setQuery(sql);
        try {
            //执行SQL语句查询
            data = query.retrieveInstances();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
