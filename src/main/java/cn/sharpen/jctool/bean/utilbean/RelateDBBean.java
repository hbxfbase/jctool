package cn.sharpen.jctool.bean.utilbean;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.sql.*;
import java.util.*;


/**
 * 关系型数据库交互对象。用于拼装增删改查所需要的sql 
 * @author Administrator
 * 2017年10月16日 上午3:18:41
 * com.ebct.util.bean.db.RelateDBBean
 */
@Data
public class RelateDBBean implements Serializable{
	public static final long serialVersionUID = 4964703003801801924L;

	/**
	 * 等于号
	 */
	public static final String eqmark="=";

	/**
	 * 反括号
	 */
	public static final String rebracket=") ";
	/**
	 * 逗号
	 */
	public static final String comma=",";
	/**
	 * 星号
	 */
	public static final String asterisk =",";
	/**
	 * sql中的and片段
	 */
	public static final String andSeg=" and ";
	/**
	 * sql中的等于片段
	 */
	public static final String eqSeg="=?";
	/**
	 * sql中的不等于片段
	 */
	public static final String neqSeg="!=?";
	/**
	 * sql中的in片段
	 */
	public static final String inSeg=" in (";
	/**
	 * sql中的not in片段
	 */
	public static final String ninSeg=" not in (";
	/**
	 * sql中的大于片段
	 */
	public static final String gtSeg=">?";
	/**
	 * sql中的小于片段
	 */
	public static final String ltSeg="<?";
	/**
	 * sql中的大于等于片段
	 */
	public static final String gteSeg=">=?";
	/**
	 * sql中的小于等于片段
	 */
	public static final String lteSeg="<=?";
	/**
	 * sql中的select片段
	 */
	public static final String selectSeg="SELECT ";
	/**
	 * sql中的from片段
	 */
	public static final String fromSeg=" FROM ";
	/**
	 * sql中的where片段
	 */
	public static final String whereSeg=" WHERE ";
	/**
	 * sql中的is null片段
	 */
	public static final String isnullSeg=" is null ";
	/**
	 * sql中的is not null片段
	 */
	public static final String isnotnullSeg=" is not null ";
	/**
	 * sql中的order by片段
	 */
	public static final String orderbySeg=" order by ";
	/**
	 * sql中的ASC片段
	 */
	public static final String ASC=" ASC ";
	/**
	 * sql中的DESC片段
	 */
	public static final String DESC=" DESC ";

	/**
	 * 数据库类型，mysql,oracle,postgresql
	 */
	private String dbType = null;
	private String table = null;
	private Long start = null;
	private Integer limit = null;

	/**
	 * or条件
	 */
	private RelateDBBean orCond = null;
	private Map<String, Object> eq = null;
	private Map<String, Object> neq = null;
	private Map<String, Collection<Object>> in = null;
	private Map<String, Collection<Object>> nin = null;
	private Map<String, Object> gt = null;
	private Map<String, Object> lt = null;
	private Map<String, Object> gte = null;
	private Map<String, Object> lte = null;
	private List<Object> nul = null;
	private List<Object> nonul = null;
	/**
	 * 排序。列名，是否正序
	 */
	private Map<String, Boolean> orderBy = null;
	/**
	 * 扩展sql,不用加and
	 */
	private Map<String, Collection<Object>> extsql=null;

	/**
	 * 用于存储条件中的对象
	 */
	private Collection<Object> valList = new ArrayList<Object>();
	/**
	 * 用于存储查询的列
	 */
	private Collection<String> queryCol = new ArrayList<String>();
	/**
	 * 用于存储要执行的sql
	 */
	private String exeSql = null;


	
	// 包装后的方法
	public void put_eq(String col, Object value){
		if(eq==null){
			eq=new LinkedHashMap<String, Object>();
		}
		eq.put(col, value);
	}
	public void put_neq(String col, Object value){
		if(neq==null){
			neq=new LinkedHashMap<String, Object>();
		}
		neq.put(col, value);
	}
	public <T> void putIn(String col, Collection<Object> value){
		if(in==null){
			in=new LinkedHashMap<String, Collection<Object>>();
		}
		in.put(col, value);
	}
	public void putNin(String col, Collection<Object> value){
		if(nin==null){
			nin=new LinkedHashMap<String, Collection<Object>>();
		}
		nin.put(col, value);
	}
	public void putGt(String col, Object value){
		if(gt==null){
			gt=new LinkedHashMap<String, Object>();
		}
		gt.put(col, value);
	}
	public void putLt(String col, Object value){
		if(lt==null){
			lt=new LinkedHashMap<String, Object>();
		}
		lt.put(col, value);
	}
	public void putGte(String col, Object value){
		if(gte==null){
			gte=new LinkedHashMap<String, Object>();
		}
		gte.put(col, value);
	}
	public void putLte(String col, Object value){
		if(lte==null){
			lte=new LinkedHashMap<String, Object>();
		}
		lte.put(col, value);
	}
	public void putNul(String col){
		if(nul==null){
			nul=new ArrayList<Object>();
		}
		nul.add(col);
	}
	public void putNonul(String col){
		if(nonul==null){
			nonul=new ArrayList<Object>();
		}
		nonul.add(col);
	}
	public void putOrderBy(String col, Boolean value){
		if(orderBy==null){
			orderBy=new LinkedHashMap<String, Boolean>();
		}
		orderBy.put(col, value);
	}

	
	public static String makeCondSql(RelateDBBean qc){
		if(qc==null){
			return null;
		}
		StringBuilder condSql = new StringBuilder(" where 1=1 ");
		Collection<Object> valList_v = qc.getValList();
		Map<String, Object> cond= null;
		List<Object> condList= null;
		Map<String, Collection<Object>> condMapList = null;

		cond = qc.getEq();
		if(MapUtils.isNotEmpty(cond)){
			for(Map.Entry<String, Object> enty : cond.entrySet()){
				if(enty.getValue()==null){
					continue;
				}
				condSql.append(andSeg).append(enty.getKey()).append(eqSeg);
				valList_v.add(enty.getValue());
			}
		}
		
		cond = qc.getNeq();
		if(MapUtils.isNotEmpty(cond)){
			for(Map.Entry<String, Object> enty : cond.entrySet()){
				if(enty.getValue()==null){
					continue;
				}
				condSql.append(andSeg).append(enty.getKey()).append(neqSeg);
				valList_v.add(enty.getValue());
			}
		}

		condMapList = qc.getIn();
		if(MapUtils.isNotEmpty(condMapList)){
			for(Map.Entry<String, Collection<Object>> enty : condMapList.entrySet()){
				if(CollectionUtils.isEmpty(enty.getValue())){
					continue;
				}
				condSql.append(andSeg).append(enty.getKey()).append(inSeg);
				for(Object obj : enty.getValue()){
					if(obj==null){
						continue;
					}
					valList_v.add(enty.getValue());
				}
				condSql.append(rebracket);
			}
		}

		condMapList = qc.getNin();
		if(MapUtils.isNotEmpty(condMapList)){
			for(Map.Entry<String, Collection<Object>> enty : condMapList.entrySet()){
				if(CollectionUtils.isEmpty(enty.getValue())){
					continue;
				}
				condSql.append(andSeg).append(enty.getKey()).append(ninSeg);
				for(Object obj : enty.getValue()){
					if(obj==null){
						continue;
					}
					valList_v.add(enty.getValue());
				}
				condSql.append(rebracket);
			}
		}

		cond = qc.getGt();
		if(MapUtils.isNotEmpty(cond)){
			for(Map.Entry<String, Object> enty : cond.entrySet()){
				condSql.append(andSeg).append(enty.getKey()).append(gtSeg);
				valList_v.add(enty.getValue());
			}
		}

		cond = qc.getLt();
		if(MapUtils.isNotEmpty(cond)){
			for(Map.Entry<String, Object> enty : cond.entrySet()){
				condSql.append(andSeg).append(enty.getKey()).append(ltSeg);
				valList_v.add(enty.getValue());
			}
		}

		cond = qc.getGte();
		if(MapUtils.isNotEmpty(cond)){
			for(Map.Entry<String, Object> enty : cond.entrySet()){
				condSql.append(andSeg).append(enty.getKey()).append(gteSeg);
				valList_v.add(enty.getValue());
			}
		}

		cond = qc.getLte();
		if(MapUtils.isNotEmpty(cond)){
			for(Map.Entry<String, Object> enty : cond.entrySet()){
				condSql.append(andSeg).append(enty.getKey()).append(lteSeg);
				valList_v.add(enty.getValue());
			}
		}

		
		condList = qc.getNul();
		if(CollectionUtils.isNotEmpty(condList)){
			for(Object col : condList){
				condSql.append(andSeg).append(col).append(isnullSeg);
			}
		}
		
		condList = qc.getNonul();
		if(CollectionUtils.isNotEmpty(condList)){
			for(Object col : condList){
				condSql.append(andSeg).append(col).append(isnullSeg);
			}
		}
		
		// 处理扩展sql
		condMapList = qc.getExtsql();
		if(MapUtils.isNotEmpty(condMapList)){
			for(Map.Entry<String, Collection<Object>> enty : condMapList.entrySet()){
				if(CollectionUtils.isEmpty(enty.getValue())){
					continue;
				}
				condSql.append(andSeg).append(enty.getKey());
				if(enty.getValue()==null){
					continue;
				}
				for(Object obj : enty.getValue()){
					if(obj==null){
						continue;
					}
					valList_v.add(enty.getValue());
				}
			}
		}
		
		// 处理order by 
		Map<String, Boolean> orderByMap = qc.getOrderBy();
		if(MapUtils.isNotEmpty(orderByMap)){
			condSql.append(orderbySeg);
			int index = 0;
			int size = orderByMap.size();
			for(Map.Entry<String, Boolean> enty : orderByMap.entrySet()){
				boolean val = enty.getValue() == null ? true : enty.getValue();
				condSql.append(enty.getKey()).append(val ? ASC : DESC);
				++index;
				if(index != size){
					condSql.append(comma);
				}
			}
		}
		qc.setExeSql(condSql.toString());
		return condSql.toString();
	}

	
	public static String makeQuerySql(RelateDBBean qc){
		return makeQuerySql(qc, null);
	}
	public static String makeQuerySql(RelateDBBean qc, String colSqlStr){
		StringBuilder colSql = new StringBuilder();
		Collection<String> queryCol_p = qc.getQueryCol();
		colSql.append(selectSeg);
		if(StringUtils.isNotBlank(colSqlStr)){
			if(colSqlStr.endsWith(comma)){
				colSqlStr = colSqlStr.substring(0, colSqlStr.length()-1);
			}
			colSql.append(colSqlStr);
		}else if(CollectionUtils.isNotEmpty(queryCol_p)){
			int i = 0, leng = queryCol_p.size();
			for(String str : queryCol_p){
				++i;
				colSql.append(str);
				if(i != leng){
					colSql.append(comma);
				}
			}
		}else{
			colSql.append(asterisk);
			
		}
		colSql.append(fromSeg).append(qc.getTable()).append(whereSeg).append(makeCondSql(qc));
		return colSql.toString();
	}
	
	public static Object exeDbAccess(RelateDBBean qc, Connection conn){
		
		return null;
		
	}
	
	/**
	 * 执行查询sql
	 * @param sql
	 * @param vals
	 * @param conn
	 * @return
	 * @author Julian
	 */
	public static <T> List<Map<String, Object>> exeQuery(String sql, List<T> vals, Connection conn) throws Exception{
		PreparedStatement ps = null;
	    ResultSet rs = null;

	    Set<Integer> dbintType = new HashSet<>(Arrays.asList(new Integer[]{
				Types.INTEGER, Types.SMALLINT, Types.TINYINT}));
	    Set<Integer> dblongType = new HashSet<>(Arrays.asList(new Integer[]{
				Types.BIGINT,  Types.LONGVARCHAR, Types.SMALLINT}));
		Set<Integer> dbstrType = new HashSet<>(Arrays.asList(new Integer[]{
				Types.CHAR, Types.NUMERIC, Types.DECIMAL, Types.DOUBLE, Types.FLOAT, Types.LONGVARCHAR, Types.LONGVARCHAR, Types.LONGVARCHAR, Types.LONGVARCHAR, Types.VARCHAR}));
		Set<Integer> dbtimeType = new HashSet<>(Arrays.asList(new Integer[]{ Types.TIME, Types.TIMESTAMP }));
		Set<Integer> dbboolType = new HashSet<>(Arrays.asList(new Integer[]{ Types.BIT}));
		
		try{
			ps = conn.prepareStatement(sql);
			for(int i=0, leng=vals.size(); i< leng; ++i){
				ps.setObject(i+1, vals.get(i));
			}
			rs = ps.executeQuery();
			if(rs == null || rs.wasNull()){
				return null;
			}
			List<Map<String, Object>> list = new ArrayList<>(); 
			ResultSetMetaData rmd = rs.getMetaData(); // http://www.cnblogs.com/chinafine/articles/1847205.html
			int rmdCount = rmd.getColumnCount();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				for(int i=0; i< rmdCount; ++i){
					int colType = rmd.getColumnType(i);
					// BIGINT, BINARY, BIT, CHAR, DATE, DECIMAL, DOUBLE, FLOAT, INTEGER, LONGVARBINARY, 
					// LONGVARCHAR, NULL, NUMERIC, OTHER, REAL, SMALLINT, TIME, TIMESTAMP, TINYINT, 
					// VARBINARY, VARCHAR
					Object val = dbintType.contains(colType) ? rs.getInt(i+1) 
							: dblongType.contains(colType) ? rs.getLong(i+1)
							: dbstrType.contains(colType) ? rs.getString(i+1)
							: dbtimeType.contains(colType) ? rs.getDate(i+1)
							: dbboolType.contains(colType) ? rs.getBoolean(i+1)
							: null;
					map.put(rmd.getColumnName(i), val);
				}
				list.add(map);
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(conn!=null){
					conn.close();
				}
			}catch(Exception e){
				throw e;
			}
		}
		return null;
	}
	

}
