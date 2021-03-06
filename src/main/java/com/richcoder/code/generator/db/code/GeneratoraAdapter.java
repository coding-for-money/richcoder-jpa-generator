package com.richcoder.code.generator.db.code;


import com.richcoder.code.generator.db.code.conf.GenerateConfig;
import com.richcoder.code.generator.db.code.vo.TableInfo;

public interface GeneratoraAdapter {

  String getTemplateName();

  default String getPackageName(GenerateConfig config, TableInfo tableInfo) {
    return getPackageName(config);
  }

  default String getPackageName(GenerateConfig config) {
    return null;
  }

  String getClassName(String modelName);
}
