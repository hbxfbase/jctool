package cn.sharpen.test.func;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.sharpen.jctool.util.JsonTool;
import cn.sharpen.jctool.util.StrTool;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static cn.hutool.core.text.StrPool.COLON;
import static cn.hutool.http.Header.AUTHORIZATION;
import static cn.sharpen.jctool.consts.SignConst.*;

/**
 * @author justin
 */
public class RqliteCheckTest {

    /**
     * rqlite数据库-创建表结构-成功
     * bcrypt使用参考文档： https://httpd.apache.org/docs/2.4/misc/password_encryptions.html
     * 成功的请求： curl -X POST -i 'http://211.144.68.135:21345/db/execute?pretty&timings' -H "Content-Type: application/json" -u "root:rqlite123A" \
     * -d '["CREATE TABLE foo5 (id INTEGER NOT NULL PRIMARY KEY, name TEXT, age INTEGER)"]'
     */
    @Test
    public void rqliteConnTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135";
        int port = 21345;
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";
        List list = ListUtil.of("CREATE TABLE foo3 (id INTEGER NOT NULL PRIMARY KEY, name TEXT, age INTEGER)");
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }

    /**
     * rqlite7创建表。用于验证注释
     * 在sql前面可以加注释。在字段的右边不能加--注释
     */
    @Test
    public void rqliteCreateTable2Test(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135";
        // rqlilte7实例的端口
        int port = 21345;
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        String createSql = "-- custom_form.sql  自定义表单\n" +
                "-- DROP TABLE IF EXISTS custom_form;\n" +
                "-- CREATE TABLE custom_form (\n" +
                "CREATE TABLE custom_form (\n" +
                "  id INTEGER DEFAULT 0 NOT NULL ,\n" +
                "  proj_id INTEGER DEFAULT 0 NOT NULL ,\n" +
                "  user_group_id INTEGER DEFAULT 0 NULL,\n" +
                "  create_user INTEGER DEFAULT 0 NULL,\n" +
                "  name TEXT DEFAULT '' NOT NULL ,\n" +
                "  remark TEXT DEFAULT '' NOT NULL ,\n" +
                "  tag TEXT DEFAULT '' NULL,\n" +
                "  col_qty INTEGER DEFAULT 0 NULL,\n" +
                "  status TEXT DEFAULT '' NULL,\n" +
                "  fill_tip TEXT DEFAULT '' NULL,\n" +
                "  fill_warn TEXT DEFAULT '' NULL,\n" +
                "  data_status TEXT DEFAULT '' NULL,\n" +
                "  create_time TEXT NOT NULL ,\n" +
                "  enable_flag TEXT DEFAULT 'Y'  NOT NULL ,\n" +
                "  PRIMARY KEY (id)\n" +
                ") ";

        List list = ListUtil.of(createSql);
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }


    /**
     * rqlite7修改表
     */
    @Test
    public void rqliteAddFieldTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135", port = "21345";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        String createSql = "ALTER TABLE custom_form ADD COLUMN tag2 TEXT DEFAULT '' NULL;";

        List list = ListUtil.of(createSql);
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }

    /**
     * rqlite7删除表字段
     */
    @Test
    public void rqliteDelFieldTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135", port = "21345";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        String createSql = "ALTER TABLE custom_form drop COLUMN fill_tip";

        String bodyJson = JsonTool.obj2json(ListUtil.of(createSql));
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }

    @Test
    public void rqliteConn2Test(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135", port = "21345";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";
        String createSql = "CREATE TABLE field_check (id INTEGER NOT NULL PRIMARY KEY, name TEXT, age INTEGER," +
                "amount1 REAL, amount2 REAL)";

        String bodyJson = JsonTool.obj2json( ListUtil.of(createSql));
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());
    }

    // rqlite批量插入-使用线程池
    static ExecutorService insertTestPool = new ThreadPoolExecutor(INT15, INT20, INT200, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(L500.intValue()), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * rqlite多线程插入单元测试。用于验证大数据量的场景下，rqlite的查询效率
     */
    @Test
    public void rqliteManyInsertTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135", port = "21345";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        int size = 1000 ;
        CountDownLatch latch = new CountDownLatch(size);

        for(int j=0 , length = 1000*100; j<length; ++j) {
            for (int i = 0; i < size; ++i) {
                insertTestPool.submit(() -> {
                    try {
                        insertOne(url, user, pwd);
                    } catch (Exception ex) {
                        StrTool.plf("rqliteBatchInsertFail={}", ex.getMessage(), ex);
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }
        ThreadUtil.safeSleep(1000*60*60*2 );
    }

    public void insertOne(String url, String user, String pwd){
        String name = RandomUtil.randomString(15);
        int age = RandomUtil.randomInt(16,70);
        String amount1 = RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008)+"."
                +RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008);
        String amount2 = RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008)+"."
                +RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008);
        String createSql = "insert into field_check(name, age, amount1, amount2 ) " +
                "values('"+name+"',"+age+",'"+amount1+"','"+amount2+"')";
        String bodyJson = JsonTool.obj2json( ListUtil.of(createSql));
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());
    }


    public String insertSql(int size){
        List<String> sqls = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            String name = RandomUtil.randomString(15);
            int age = RandomUtil.randomInt(16,70);
            String amount1 = RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008)+"."
                    +RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008);
            String amount2 = RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008)+"."
                    +RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008);
            String createSql = "insert into field_check(name, age, amount1, amount2 ) " +
                    "values('"+name+"',"+age+",'"+amount1+"','"+amount2+"')";
            sqls.add(createSql);
        }

        String bodyJson = JsonTool.obj2json( ListUtil.of(sqls));
        return bodyJson;
    }

    public void insertBatch(String url, String user, String pwd, int size){
        String bodyJson = insertSql(size);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        long t1 = System.nanoTime();
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("insertBatchSpendMilli={} respBody={}", StrTool.spendMilli(t1), resp.body());
    }

    @Test
    public void batchInsertSqlTest(){
        batchInsertSql();
    }

    public String batchInsertSql(){
        int size = 1000 ;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            String name = RandomUtil.randomString(15);
            int age = RandomUtil.randomInt(16,70);
            String amount1 = RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008)+"."
                    +RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008);
            String amount2 = RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008)+"."
                    +RandomUtil.randomLong(1000*1000*100,1000*1000*100*1000008);
            String createSql = "insert into field_check(name, age, amount1, amount2 ) " +
                    "values('"+name+"',"+age+",'"+amount1+"','"+amount2+"')";
            sb.append(createSql).append(";");
        }
        StrTool.plf("insertBatchRespBody={}", sb);
        return sb.toString();
    }

    /**
     * rqlite批量插入单元测试。用于验证大数据量的场景下，rqlite的查询效率
     */
    @Test
    public void rqliteBatchInsertTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135", port = "21345";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        int size = 1000 , length = 1000*100 ;
        for(int j=0; j<length; ++j) {
            insertBatch(url, user, pwd, size);
        }
        ThreadUtil.safeSleep(1000*60*60*2 );
    }

    /**
     * 把多个sql拼在一起作为json数组的仅仅一个元素，插入效率最高
     */
    @Test
    public void rqliteBatchInsert2Test(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135", port = "21345";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        int length = 1000*100 ;
        for(int j=0; j<length; ++j) {
            String sqls = batchInsertSql();
            String bodyJson = JsonTool.obj2json( ListUtil.of(sqls));
            long t1 = System.nanoTime();
            HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                    .body(bodyJson).execute();
            StrTool.plf("spendMilli={} respBody={}", StrTool.spendMilli(t1), resp.body());
        }
        ThreadUtil.safeSleep(1000*60*60*2 );
    }


    @Test
    public void createTableLocalTest(){
        String user="root", pwd ="admin123Ab", ip ="localhost", port = "3317";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";
        String createSql = "CREATE TABLE field_check (id INTEGER NOT NULL PRIMARY KEY, name TEXT, age INTEGER," +
                "amount1 REAL, amount2 REAL)";

        String bodyJson = JsonTool.obj2json( ListUtil.of(createSql));
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());
    }

    /**
     * 把多个sql拼在一起作为json数组的仅仅一个元素，插入效率最高
     */
    @Test
    public void batchInsertLocalTest(){
        String user="root", pwd ="admin123Ab", ip ="localhost", port = "3317";
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        int length = 100 ;
        for(int j=0; j<length; ++j) {
            String sqls = batchInsertSql();
            String bodyJson = JsonTool.obj2json( ListUtil.of(sqls));
            long t1 = System.nanoTime();
            HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                    .body(bodyJson).execute();
            StrTool.plf("spendMilli={} respBody={}", StrTool.spendMilli(t1), resp.body());
        }
        ThreadUtil.safeSleep(1000*60*60*2 );
    }

    /**
     * 查询rqlite状态-成功
     * 参考： https://rqlite.io/docs/guides/monitoring-rqlite/
     */
    @Test
    public void rqliteStatusTest() {
        String user = "root", pwd = "rqlite123A", ip = "211.144.68.135";
        // 内网端口： 3312
        int port = 21345;
        String url = "http://" + ip + COLON + port + "/status?pretty";
        HttpResponse resp= HttpRequest.get(url).basicAuth(user, pwd).execute();

        StrTool.plf("url={} respBody={}", url, resp.body());
    }

    /**
     * 查询rqlite节点
     * 参考： https://rqlite.io/docs/guides/monitoring-rqlite/
     */
    @Test
    public void rqliteNodeTest() {
        String user = "root", pwd = "rqlite123A", ip = "211.144.68.135";
        // 内网端口： 3312
        int port = 21345;
        String url = "http://" + ip + COLON + port + "/nodes?pretty";
        HttpResponse resp= HttpRequest.get(url).basicAuth(user, pwd).execute();

        StrTool.plf("url={} respBody={}", url, resp.body());
    }

    /**
     * 参考： https://rqlite.io/docs/guides/monitoring-rqlite/
     */
    @Test
    public void rqliteReadyzTest() {
        String user = "root", pwd = "rqlite123A", ip = "211.144.68.135";
        // 内网端口： 3312
        int port = 21345;
        String url = "http://" + ip + COLON + port + "/readyz?pretty";
        HttpResponse resp= HttpRequest.get(url).basicAuth(user, pwd).execute();

        StrTool.plf("url={} respBody={}", url, resp.body());
    }

    /**
     * rqlite的bcrypt数据库连接-失败
     * bcrypt使用参考文档： https://httpd.apache.org/docs/2.4/misc/password_encryptions.html
     */
    @Test
    public void rqliteConnBcryptTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135";
        int port = 21345;
        pwd = BCrypt.hashpw(user+COLON +pwd );
        String auth = "Basic "+ pwd;
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";
        List list = ListUtil.of("CREATE TABLE foo3 (id INTEGER NOT NULL PRIMARY KEY, name TEXT, age INTEGER)");
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={} auth={}", url, bodyJson, auth);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).body(bodyJson)
                .header(AUTHORIZATION, auth)
                .execute();
        StrTool.plf("respBody={}", resp.body());

    }

    /**
     * rqlite插入数据-成功
     * 成功的请求： curl -X POST -i 'http://211.144.68.135:21345/db/execute?pretty&timings' -H "Content-Type: application/json" -u "root:rqlite123A" \
     * -d '["INSERT INTO foo(name) VALUES(\'fiona22\')"]'
     */
    @Test
    public void rqliteUpdateTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135";
        int port = 21345;
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";
        List list = ListUtil.of("INSERT INTO foo(name) VALUES('fiona22')");
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }


    /**
     * rqlite查询数据-成功
     * 参考文档： https://rqlite.io/docs/api/api/
     * 成功的请求： curl -X POST -i 'http://211.144.68.135:21345/db/query?pretty&timings' -H "Content-Type: application/json" -u "root:rqlite123A" \
     * -d '["SELECT * FROM foo"]'
     */
    @Test
    public void rqliteQueryTest(){
        String user="root", pwd ="rqlite123A", ip ="211.144.68.135";
        int port = 21345;
        String url = "http://"+ip+COLON+port+"/db/query?pretty&timings";
        List list = ListUtil.of("SELECT * FROM foo");
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());


        url = "http://"+ip+COLON+port+"/db/query?timings";
        list = ListUtil.of("SELECT * FROM foo");
        bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }

    // 下面是连接性测试

    /**
     * rqlite数据库-创建表结构-失败
     * bcrypt使用参考文档： https://httpd.apache.org/docs/2.4/misc/password_encryptions.html
     * 成功的请求： curl -X POST -i 'http://211.144.68.135:21345/db/execute?pretty&timings' -H "Content-Type: application/json" -u "root:rqlite123A" \
     * -d '["CREATE TABLE foo5 (id INTEGER NOT NULL PRIMARY KEY, name TEXT, age INTEGER)"]'
     */
    @Test
    public void rqliteConnTaTestTest(){
        String user="root", pwd ="admin123Ab", ip ="121.127.232.31";
        int port = 3327;
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";
        List list = ListUtil.of("CREATE TABLE foo3 (id INTEGER NOT NULL PRIMARY KEY, name TEXT, age INTEGER)");
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);
        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }


    @Test
    public void rqliteCreateTable(){
        String user="root", pwd ="admin123Ab", ip ="localhost";
        String sqlDir = "D:\\datum\\code\\pve3git\\bcns\\bcsdata\\doc\\sql_dir";
        int port = 3317;
        String url = "http://"+ip+COLON+port+"/db/execute?pretty&timings";

        List<String> list = new ArrayList();
        List<File> files = FileUtil.loopFiles(sqlDir);
        for(File file : files) {
            list.add(FileUtil.readUtf8String(file));
        }
        String bodyJson = JsonTool.obj2json(list);
        StrTool.plf("reqUrl={} reqBody={}", url, bodyJson);

        HttpResponse resp= HttpRequest.post(url).contentType(ContentType.JSON.getValue()).basicAuth(user, pwd)
                .body(bodyJson).execute();
        StrTool.plf("respBody={}", resp.body());

    }


}
