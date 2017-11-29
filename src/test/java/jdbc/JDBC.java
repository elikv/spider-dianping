package jdbc;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;


  
public class JDBC {  
  
    // 表示定义数据库的用户名  
    private static String USERNAME ="root";  
  
    // 定义数据库的密码  
    private static String PASSWORD="xiaofeiyang";  
  
    // 定义数据库的驱动信息  
    private static String DRIVER = "com.mysql.jdbc.Driver";  
  
    // 定义访问数据库的地址  bp1853qt67sqaf4svo
    private static String URL = "jdbc:mysql://123.206.206.111:3306/spider?characterEncoding=UTF-8&characterSetResults=UTF-8&zeroDateTimeBehavior=convertToNull";  
  
    // 定义数据库的链接  
    private Connection connection;  
  
    // 定义sql语句的执行对象  
    private PreparedStatement pstmt;  
  
    // 定义查询返回的结果集合  
    private ResultSet resultSet;  
      
  

  
    public JDBC() {  
  
    }  
  
    /** 
     * 获取数据库连接 
     *  
     * @return 数据库连接 
     */  
    public Connection getConnection() {  
        try {  
            Class.forName(DRIVER); // 注册驱动  
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获取连接  
        } catch (Exception e) {  
            throw new RuntimeException("get connection error!", e);  
        }  
        return connection;  
    }  
  
    /** 
     * 执行更新操作 
     *  
     * @param sql 
     *            sql语句 
     * @param params 
     *            执行参数 
     * @return 执行结果 
     * @throws SQLException 
     */  
    public boolean updateByPreparedStatement(String sql, List<?> params)  
            throws SQLException {  
        boolean flag = false;  
        int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数  
        pstmt = connection.prepareStatement(sql);  
        int index = 1;  
        // 填充sql语句中的占位符  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        result = pstmt.executeUpdate();  
        flag = result > 0 ? true : false;  
        return flag;  
    } 
    
    public boolean deleteById(String id)  
            throws SQLException {  
    	int result = -1;
        pstmt = connection.prepareStatement("delete from t_shop where id=?");  
        // 填充sql语句中的占位符  
        pstmt.setObject(1, id);  
        result = pstmt.executeUpdate();  
        boolean flag = result > 0 ? true : false;  
        return flag;  
    } 
  
    /** 
     * 执行查询操作 
     *  
     * @param sql 
     *            sql语句 
     * @param params 
     *            执行参数 
     * @return 
     * @throws SQLException 
     */  
    public List<Map<String, Object>> findResult(String sql, List<?> params)  
            throws SQLException {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        int index = 1;  
        pstmt = connection.prepareStatement(sql);  
        if (params != null && !params.isEmpty()) {  
            for (int i = 0; i < params.size(); i++) {  
                pstmt.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pstmt.executeQuery();  
        ResultSetMetaData metaData = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while (resultSet.next()) {  
            Map<String, Object> map = new HashMap<String, Object>();  
            for (int i = 0; i < cols_len; i++) {  
                String cols_name = metaData.getColumnName(i + 1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if (cols_value == null) {  
                    cols_value = "";  
                }  
                map.put(cols_name, cols_value);  
            }  
            list.add(map);  
        }  
        return list;  
    }  
  
    /** 
     * 释放资源 
     */  
    public void releaseConn() {  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (pstmt != null) {  
            try {  
                pstmt.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
        if (connection != null) {  
            try {  
                connection.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    public static List<String> findShopByUrl(String url) throws IllegalAccessException, InvocationTargetException{  
        String sql = "select * from t_shop where url=? ORDER BY createTime desc ";  
        //创建填充参数的list  
        List<Object> paramList = new ArrayList<Object>(); 
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        //填充参数  
        paramList.add(url);  
        List<String> shop = new ArrayList<String>();
        JDBC jdbcUtil = null;  
        try {  
            jdbcUtil = new JDBC();  
            jdbcUtil.getConnection(); // 获取数据库链接  
            mapList = jdbcUtil.findResult(  
                    sql.toString(), paramList);  
            if(mapList.size()>1){  
            	for (Map<String, Object> map : mapList) {
            		String object = (String) map.get("id");
            		shop.add(object);
				}
            	shop.remove(0);
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            if (jdbcUtil != null) {  
                jdbcUtil.releaseConn(); // 一定要释放资源  
            }  
        }
		return shop;  
  
    }  
    
    public static List<Map<String, Object>> findAll() throws IllegalAccessException, InvocationTargetException{  
        String sql = "select * from t_shop group BY shopName having count(shopName)>1";  
        //创建填充参数的list  
        List<Object> paramList = new ArrayList<Object>();  
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        //填充参数  
        String shop = "";
        JDBC jdbcUtil = null;  
        try {  
            jdbcUtil = new JDBC();  
            jdbcUtil.getConnection(); // 获取数据库链接  
             mapList = jdbcUtil.findResult(  
                    sql.toString(), paramList);  
        } catch (SQLException e) {  
            e.printStackTrace();  
        } finally {  
            if (jdbcUtil != null) {  
                jdbcUtil.releaseConn(); // 一定要释放资源  
            }  
        }
		return mapList;  
  
    }  
    private static int insert(String student) {
    	JDBC jdbcUtil = null;  
    	jdbcUtil = new JDBC();  
        Connection conn = jdbcUtil.getConnection(); // 获取数据库链接  
        int i = 0;
        String sql = "insert into t_deposit_record(commonCode,shopName,businessDate,status)values(?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, student);
            pstmt.setString(2, student);
            pstmt.setString(3, "2017-11-19");
            pstmt.setString(4, "未存");
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public void removeDuplicate() throws SQLException, IllegalAccessException, InvocationTargetException{
    	JDBC jdbcUtil = null;  
    	jdbcUtil = new JDBC();
    	jdbcUtil.getConnection();
    	List<Map<String, Object>> allData = findAll();
    	List<String> deleteIds = new ArrayList<String>();
    	for (Map<String, Object> map : allData) {
    		List<String> findShopByUrl = findShopByUrl((String)map.get("url"));
    		if(!CollectionUtils.isEmpty(findShopByUrl)){
    			deleteIds.addAll(findShopByUrl);
    		};
		}
    	
		for (String string : deleteIds) {
			jdbcUtil.deleteById(string)	;
		}
    	
    	
    }
 
    
  
    	
}  

