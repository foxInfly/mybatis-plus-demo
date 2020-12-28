package com.lp.test.mybatisplusdemo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.lp.test.mybatisplusdemo.modules.test.domain.po.User;
import com.lp.test.mybatisplusdemo.modules.test.service.UserService;
import com.lp.test.mybatisplusdemo.poi.domain.po.TodayCount;
import com.lp.test.mybatisplusdemo.poi.service.TodayCountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


//@SpringBootTest
//@RunWith(SpringRunner.class)
public class MybatisPlusDemoApplicationTests {

//    @Resource
//    private UserMapper userMapper;
    @Resource
    private UserService userService;
    @Resource
    private TodayCountService todayCountService;

    @Test
    public void testgetOne() {
        System.out.println(("----- getOne method test ------"));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> id = queryWrapper.eq("id", 13);
        User user = userService.getOne(queryWrapper);
            System.out.println(user);
    }

    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userService.list();
        for (User user : userList) {
            System.out.println(user);
        }
    }
    @Test
    public void testSave() {
        User user = new User();
        user.setName("tom").setAge(20).setEmail("tom@163.com");
        // 手动添加数据
        if (userService.save(user)) {
            userService.list().forEach(System.out::println);
        } else {
            System.out.println("添加数据失败");
        }
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(6L).setName("tom").setAge(20).setEmail("tom@163.com");
        // 手动添加数据
        if (userService.updateById(user)) {
            userService.list().forEach(System.out::println);
        } else {
            System.out.println("添加数据失败");
        }
    }

    @Test
    public void tesDel() {
        User user = new User();
        // 手动添加数据
        if (userService.removeById(6L)) {
            userService.list().forEach(System.out::println);
        } else {
            System.out.println("添加数据失败");
        }
    }

    @Test
    public void tesVersion() {
        User user = new User()
                .setName("tom")
                .setAge(20)
                .setEmail("lp@163.com");
        userService.save(user);
        userService.list().forEach(System.out::println);
        System.out.println("================");
        user.setName("lily");
        userService.update(user, null);
        userService.list().forEach(System.out::println);
    }

    @Test
    public void testPage() {
        // Step1：创建一个 Page 对象
        Page<User> page = new Page<>();
        // Page<User> page = new Page<>(2, 5);
        // Step2：调用 mybatis-plus 提供的分页查询方法
        userService.page(page, null);
        // Step3：获取分页数据
        System.out.println(page.getCurrent()); // 获取当前页
        System.out.println(page.getTotal()); // 获取总记录数
        System.out.println(page.getSize()); // 获取每页的条数
        System.out.println(page.getRecords()); // 获取每页数据的集合
        System.out.println(page.getPages()); // 获取总页数
        System.out.println(page.hasNext()); // 是否存在下一页
        System.out.println(page.hasPrevious()); // 是否存在上一页
    }

    /**
     * Wrapper  条件构造抽象类
     *     -- AbstractWrapper 查询条件封装，用于生成 sql 中的 where 语句。
     *         -- QueryWrapper Entity 对象封装操作类，用于查询。
     *         -- UpdateWrapper Update 条件封装操作类，用于更新。
     *     -- AbstractLambdaWrapper 使用 Lambda 表达式封装 wrapper
     *         -- LambdaQueryWrapper 使用 Lambda 语法封装条件，用于查询。
     *         -- LambdaUpdateWrapper 使用 Lambda 语法封装条件，用于更新。
     * 【QueryWrapper 条件：】
     *     select(String... sqlSelect); // 用于定义需要返回的字段。例： select("id", "name", "age") ---> select id, name, age
     *     select(Predicate<TableFieldInfo> predicate); // Lambda 表达式，过滤需要的字段。
     *     lambda(); // 返回一个 LambdaQueryWrapper
     *
     * 【UpdateWrapper 条件：】
     *     set(String column, Object val); // 用于设置 set 字段值。例: set("name", null) ---> set name = null
     *     etSql(String sql); // 用于设置 set 字段值。例: setSql("name = '老李头'") ---> set name = '老李头'
     *     lambda(); // 返回一个 LambdaUpdateWrapper
     */
    @Test
    public void testQueryWrapper() {
        // Step1：创建一个 QueryWrapper 对象,拼装SQL
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // Step2： 构造查询条件
        //eq、ne、gt、ge、lt、le
        //between、notBetween、in、notIn、inSql、notInSql
        //like、notLike、likeLeft、likeRight
        //isNull、isNotNull
        //groupBy、orderByAsc、orderByDesc、having
        //or、or、and、nested、apply、last、exists
        String a = "2";
        queryWrapper
                .select("id", "name", "age")
//                .eq("age", 20)
//                .ne("age", 20)
//                .gt("age", 20)
//                .ge("age", 20)
//                .lt("age", 20)
//                .le("age", 20)
//                .between("age", 19,22)//va1< col <=v2,前开后闭
//                .in("age", 19,22)
                .inSql("age","select age from user where age > "+a)
                .like("name", "j")
                .groupBy("id","name","age")
                .orderByDesc("age");

        // Step3：执行查询
        userService
                .list(queryWrapper)
                .forEach(System.out::println);
    }

    @Test
    public void testTodaySelectList() {
        List<TodayCount> todayCounts = todayCountService.list();
        for (TodayCount user : todayCounts) {
            System.out.println(user);
        }
    }

    @Test
    public void testGenerator() {
        // Step1：代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // Step2：全局配置,指定代码输出路径，以及包名、作者等信息。
        GlobalConfig gc = new GlobalConfig();
        // 填写代码生成的目录(需要修改)
        String projectPath = "G:\\project\\Idea\\demo\\mybatis-plus-demo";
        // 拼接出代码最终输出的目录
        gc.setOutputDir(projectPath + "/src/main/java");
        // 配置开发者信息（可选）（需要修改）
        gc.setAuthor("lp");
        // 配置是否打开目录，false 为不打开（可选）
        gc.setOpen(false);
        // 实体属性 Swagger2 注解，添加 Swagger 依赖，开启 Swagger2 模式（可选）
        //gc.setSwagger2(true);
        // 重新生成文件时是否覆盖，false 表示不覆盖（可选）
        gc.setFileOverride(false);
        // 配置主键生成策略，此处为 ASSIGN_ID（可选）
        gc.setIdType(IdType.AUTO);
        // 配置日期类型，此处为 ONLY_DATE（可选）
        gc.setDateType(DateType.ONLY_DATE);
        // 默认生成的 service 会有 I 前缀
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // Step3：数据源配置（需要修改）,用于指定 需要生成代码的 数据仓库、数据表。setUrl、setDriverName、setUsername、setPassword 均需修改。
        DataSourceConfig dsc = new DataSourceConfig();
        // 配置数据库 url 地址
        dsc.setUrl("jdbc:mysql://114.55.95.30:3306/test-01?serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull");
        // dsc.setSchemaName("testMyBatisPlus"); // 可以直接在 url 中指定数据库名
        // 配置数据库驱动
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        // 配置数据库连接用户名
        dsc.setUsername("root");
        // 配置数据库连接密码
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // Step:4：包配置,setParent、setModuleName 均需修改。其余按需求修改.
        PackageConfig pc = new PackageConfig();
        // 配置父包名（需要修改）
        pc.setParent("com.lp.test.mybatisplusdemo");
        // 配置模块名（需要修改）
        pc.setModuleName("test");
        // 配置 entity 包名
        pc.setEntity("entity");
        // 配置 mapper 包名
        pc.setMapper("mapper");
        // 配置 service 包名
        pc.setService("service");
        // 配置 controller 包名
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // Step5：策略配置（数据库表配置）,setInclude 需要修改，其余按实际开发修改。
        StrategyConfig strategy = new StrategyConfig();
        // 指定表名（可以同时操作多个表，使用 , 隔开）（需要修改）
        strategy.setInclude("user");
        // 配置数据表与实体类名之间映射的策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 配置数据表的字段与实体类的属性名之间映射的策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 配置 lombok 模式
        strategy.setEntityLombokModel(true);
        // 配置 rest 风格的控制器（@RestController）
        strategy.setRestControllerStyle(true);
        // 配置驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 配置表前缀，生成实体时去除表前缀
        // 此处的表名为 test_mybatis_plus_user，模块名为 test_mybatis_plus，去除前缀后剩下为 user。
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);

        // Step6：执行代码生成操作
        mpg.execute();
    }


}
