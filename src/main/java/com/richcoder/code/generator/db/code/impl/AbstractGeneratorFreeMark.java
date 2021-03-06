package com.richcoder.code.generator.db.code.impl;

import com.richcoder.code.generator.IGenerator;
import com.richcoder.code.generator.db.code.GeneratoraAdapter;
import com.richcoder.code.generator.db.code.LoadDBInfo;
import com.richcoder.code.generator.db.code.conf.GenerateConfig;
import com.richcoder.code.generator.db.code.vo.TableInfo;
import com.richcoder.code.generator.util.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;


public abstract class AbstractGeneratorFreeMark implements IGenerator {

  @Override
  public void generator(GenerateConfig config, GeneratoraAdapter adapter) {
    Configuration configuration = new Configuration();
    configuration.setDefaultEncoding("UTF-8");
    configuration.setClassForTemplateLoading(LoadDBInfo.class, config.getTemplateBasePath());
    Template t = null;
    try {
      t = configuration.getTemplate(adapter.getTemplateName());
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    for (TableInfo tableInfo : config.getTableInfos()) {
      String packageName = adapter.getPackageName(config, tableInfo);
      String path = config.getFilePath() + File.separator + StringUtils
          .replaceChars("src.main.java." + packageName, ".", File.separator);
      try {
        Map<String, Object> map = new HashMap<>();
        map.put("tableInfo", tableInfo);
        map.put("config", config);
        map.put("packageName", packageName);
        FileWriter writer = FileUtil
            .getWriter(path, adapter.getClassName(tableInfo.getClassName()));
        if (writer != null) {
          t.process(map, writer);
          writer.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
