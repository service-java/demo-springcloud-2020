package cn.jiiiiiin.module.common.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 代码生成器：
 * <p>
 * 目前实现，根据`./jiiiiiin-security/jiiiiiin-module-common/src/main/resources/config/generator.properties`属性文件声明{@link Config}对应：
 * <p>
 * + 模块前缀：{@link Config#modulePrefix}
 * + 创建作者：{@link Config#author}
 * + 数据库jdbc链接url：{@link Config#dataSourceUrl}
 * + 数据库链接用户名：{@link Config#dataSourceUserName}
 * + 数据库链接密码：{@link Config#dataSourcePassword}
 * + 文件生成跟路径【可选】：{@link Config#generatorRootPath}
 * <p>
 * 如生成单表`sys_admin`示例：
 * <p>
 * 将会创建一下文件：
 * <p>
 * .
 * ├── [         96]  jiiiiiin-module-common
 * │   └── [         96]  src
 * │       └── [        128]  main
 * │           ├── [         96]  java
 * │           │   └── [         96]  cn
 * │           │       └── [         96]  jiiiiiin
 * │           │           └── [         96]  module
 * │           │               └── [        128]  common
 * │           │                   ├── [         96]  entity
 * │           │                   │   └── [         96]  user
 * │           │                   │       └── [       1508]  Admin.java
 * │           │                   └── [         96]  mapper
 * │           │                       └── [         96]  user
 * │           │                           └── [        311]  AdminMapper.java
 * │           └── [         96]  resources
 * │               └── [         96]  mapper
 * │                   └── [         96]  user
 * │                       └── [        930]  AdminMapper.xml
 * └── [         96]  jiiiiiin-module-user
 * └── [         96]  src
 * └── [         96]  main
 * └── [        128]  java
 * ├── [         96]  controller
 * │   └── [        405]  AdminController.java
 * └── [        128]  service
 * ├── [        281]  IAdminService.java
 * └── [         96]  impl
 * └── [        484]  AdminServiceImpl.java
 * <p>
 * 22 directories, 6 files
 * <p>
 * 其中：
 * + entity/mapper(xml)将生成到统一模块`[modulePrefix]-module-common`;
 * + controller/service(impl)生成到对应的模块
 * <p>
 * 注意：
 * + 目前不支持在统一模块生成`sys_role/mng_role`这样的表前缀不同但是后面相同的代码，除非，不设置`请输入表前缀`，故如果出现这样的情况，应该放在不同的模块！
 *
 * @author jiiiiiin
 */
public class CodeGenerator {

    private static final String FLAG_SCANNER_SPLIT = ",";
    private static final String FLAG_TABLE_PREFIX_SPLIT = "_";

    public static void main(String[] args) {

        val config = Config
                .newInstance(ResourceBundle.getBundle("config/generator"))
                .setModuleName(scanner("模块名"));

        val tableNames = scannerReturnArr("表名", null);

        // 代码生成器
        new AutoGenerator()
                // 全局配置
                .setGlobalConfig(new GlobalConfig()
                        .setOutputDir(config.generatorRootPath + "/" + config.modulePrefix + "-module" + "-" + config.moduleName + "/src/main/java")
                        .setAuthor(config.author)
                        .setFileOverride(scannerConfirm("是否覆盖原有文件"))
                        .setOpen(false)
                        .setEnableCache(true)
                        .setIdType(IdType.ID_WORKER)
                        .setBaseResultMap(true)
                        .setActiveRecord(true)
                        .setBaseColumnList(true)
                        .setSwagger2(true)
                )
                // 数据源配置，通过该配置，指定需要生成代码的具体数据库
                .setDataSource(new DataSourceConfig()
                        .setUrl(config.dataSourceUrl)
                        .setDriverName(getDbDriverName(config.dataSourceUrl))
                        .setUsername(config.dataSourceUserName)
                        .setPassword(config.dataSourcePassword)
                )
                // 包配置
                .setPackageInfo(new PackageConfig()
                        .setParent(null)
                        .setController(config.modulePackage + ".controller")
                        .setService(config.modulePackage + ".service")
                        .setServiceImpl(config.modulePackage + ".service.impl")
                        .setEntity(String.format(config.packageInfoEntity, config.moduleName))
                        .setMapper(String.format(config.packageInfoMapper, config.moduleName))
                )
                // 策略配置
                .setStrategy(new StrategyConfig()
                        .setInclude(tableNames)
                        .setTablePrefix(parseTablePrefixes(tableNames))
                        .setSuperEntityColumns("id")
                        .setNaming(NamingStrategy.underline_to_camel)
                        .setColumnNaming(NamingStrategy.underline_to_camel)
                        .setSuperEntityClass(config.strategySuperEntityClass)
                        .setEntityColumnConstant(true)
                        .setEntityLombokModel(true)
                        .setEntityBuilderModel(true)
                        .entityTableFieldAnnotationEnable(true)
                        .setSuperControllerClass(config.strategySuperControllerClass)
                        .setRestControllerStyle(true)
                        .setControllerMappingHyphenStyle(true)
                )
                .setCfg(injectionConfig(config, config.generatorRootPath, config.moduleName))
                .setTemplate(new TemplateConfig()
                        .setEntity(null)
                        .setMapper(null)
                        .setXml(null))
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    private static InjectionConfig injectionConfig(Config config, String projectPath, String moduleName) {
        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        val finalCommonPath = projectPath + "/" + config.commonModuleName;
        focList.add(new FileOutConfig("/templates/custom_entity.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return finalCommonPath + config.entityOutputPackage + moduleName
                        + "/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/mapper.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return finalCommonPath + config.mapperOutputPackage + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return finalCommonPath + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    @Data
    @Accessors(chain = true)
    public static class Config {
        /**
         * 文件生成跟路径
         * <p>
         * 可选，建议直接生成到`System.getProperty("user.dir")`项目对应目录，如果不小心覆盖了，可以使用git进行`compare last same version -> merge`
         */
        private String generatorRootPath;
        /**
         * 数据库jdbc链接url
         */
        private String dataSourceUrl;
        private String dataSourceUserName;
        private String dataSourcePassword;
        /**
         * 模块前缀
         */
        private String modulePrefix = "jiiiiiin";
        /**
         * 创建作者
         */
        private String author = "jiiiiiin";
        private String moduleName;
        private String modulePackage;
        private String commonModuleName = modulePrefix + "-module-common";
        private String entityOutputPackage = "/src/main/java/cn/" + modulePrefix + "/module/common/entity/";
        private String mapperOutputPackage = "/src/main/java/cn/" + modulePrefix + "/module/common/mapper/";
        private String packageInfoEntity = "cn." + modulePrefix + ".module.common.entity.%s";
        private String packageInfoMapper = "cn." + modulePrefix + ".module.common.mapper.%s";
        private String strategySuperEntityClass = "cn." + modulePrefix + ".data.orm.entity.BaseEntity";
        private String strategySuperControllerClass = "cn." + modulePrefix + ".module.common.controller.BaseController";

        public static Config newInstance(ResourceBundle rb) {
            val conf = new Config();
            String _pp;
            try {
                _pp = rb.getString("generator.project.PATH");
            } catch (Exception e) {
                _pp = System.getProperty("user.dir");
            }
            conf.setGeneratorRootPath(_pp);
            conf.setModulePrefix(rb.getString("generator.module.prefix"));
            conf.setDataSourceUrl(rb.getString("generator.datasource.url"));
            conf.setDataSourceUserName(rb.getString("generator.datasource.username"));
            conf.setDataSourcePassword(rb.getString("generator.datasource.password"));
            conf.setAuthor(rb.getString("generator.author"));
            return conf;
        }

        public Config setModulePrefix(String modulePrefix) {
            this.modulePrefix = modulePrefix;
            this.commonModuleName = this.modulePrefix + "-module-common";
            this.entityOutputPackage = "/src/main/java/cn/" + this.modulePrefix + "/module/common/entity/";
            this.entityOutputPackage = "/src/main/java/cn/" + this.modulePrefix + "/module/common/entity/";
            this.mapperOutputPackage = "/src/main/java/cn/" + this.modulePrefix + "/module/common/mapper/";
            this.packageInfoEntity = "cn." + this.modulePrefix + ".module.common.entity.%s";
            this.packageInfoMapper = "cn." + this.modulePrefix + ".module.common.mapper.%s";
            this.strategySuperEntityClass = "cn." + this.modulePrefix + ".data.orm.entity.BaseEntity";
            this.strategySuperControllerClass = "cn." + this.modulePrefix + ".module.common.controller.BaseController";
            return this;
        }

        public Config setModuleName(String moduleName) {
            this.moduleName = moduleName;
            this.modulePackage = "cn." + this.modulePrefix + ".module." + this.moduleName;
            return this;
        }
    }

    private static String[] parseTablePrefixes(@NonNull String[] tableNames) {
        var res = new HashSet<String>(tableNames.length);
        for (String tableName : tableNames) {
            if (tableName.contains(FLAG_TABLE_PREFIX_SPLIT)) {
                res.add(tableName.split(FLAG_TABLE_PREFIX_SPLIT)[0]);
            }
        }
        return res.toArray(new String[tableNames.length]);
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private static boolean scannerConfirm(@NonNull String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("* " + tip + "：y/n");
        System.out.println(help.toString());
        // 现在有输入数据
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            return StringUtils.equalsIgnoreCase("y", ipt);
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private static String[] scannerReturnArr(@NonNull String tip, String[] defRes) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String[] ipt = scanner.next().split(FLAG_SCANNER_SPLIT);
            if (ipt.length > 0) {
                return ipt;
            } else if (defRes != null) {
                return defRes;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static String getDbDriverName(@NonNull String url) {
        String res;
        if (url.contains("mysql")) {
            res = "com.mysql.jdbc.Driver";
        } else {
            throw ExceptionUtils.mpe("Unknown TYPE of database!");
        }
        return res;
    }

}
