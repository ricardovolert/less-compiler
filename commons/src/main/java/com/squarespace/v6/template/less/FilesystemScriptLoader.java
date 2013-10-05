package com.squarespace.v6.template.less;

import static com.squarespace.v6.template.less.core.ExecuteErrorMaker.importError;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;


public class FilesystemScriptLoader implements ScriptLoader {

  @Override
  public String load(Path path) throws LessException {
    return readFile(path);
  }

  private String readFile(Path path) throws LessException {
    try (InputStream input = Files.newInputStream(path)) {
      return IOUtils.toString(input);
      
    } catch (IOException e) {
      throw new LessException(importError(path, e.getMessage()));
    }
  }

}
