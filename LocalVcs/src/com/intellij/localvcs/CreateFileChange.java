package com.intellij.localvcs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CreateFileChange extends Change {
  // todo test storing of all the changes and test it once and for all times 8))

  private Integer myId;
  private Path myPath;
  private String myContent;
  private Long myTimestamp;

  private IdPath myAffectedEntryIdPath;

  public CreateFileChange(Integer id, String path, String content, Long timestamp) {
    myId = id;
    myPath = new Path(path);
    myContent = content;
    myTimestamp = timestamp;
  }

  public CreateFileChange(Stream s) throws IOException {
    myPath = s.readPath();
    myId = s.readInteger();
    myContent = s.readString();
  }

  @Override
  public void write(Stream s) throws IOException {
    s.writePath(myPath);
    s.writeInteger(myId);
    s.writeString(myContent);
  }

  public Path getPath() {
    return myPath;
  }

  public String getContent() {
    return myContent;
  }

  @Override
  public void applyTo(RootEntry root) {
    root.doCreateFile(myId, myPath, myContent, myTimestamp);
    myAffectedEntryIdPath = root.getEntry(myPath).getIdPath();
  }

  @Override
  public void _revertOn(RootEntry root) {
    root.doDelete(myPath);
  }

  @Override
  protected List<IdPath> getAffectedEntryIdPaths() {
    return Arrays.asList(myAffectedEntryIdPath);
  }
}
