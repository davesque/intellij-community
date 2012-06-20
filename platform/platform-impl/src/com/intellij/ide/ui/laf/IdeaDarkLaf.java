/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.ide.ui.laf;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.ColorUtil;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

/**
 * @author Konstantin Bulenkov
 */
final class IdeaDarkLaf extends BasicLookAndFeel {
  BasicLookAndFeel base;
  public IdeaDarkLaf() {
    try {
      final String name = UIManager.getSystemLookAndFeelClassName();
      base = (BasicLookAndFeel)Class.forName(name).newInstance();
    }
    catch (Exception ignore) {
      log(ignore);
    }
  }

  private void callInit(String method, UIDefaults defaults) {
    try {
      final Method superMethod = base.getClass().getDeclaredMethod(method, UIDefaults.class);
      superMethod.setAccessible(true);
      superMethod.invoke(base, defaults);
    }
    catch (Exception ignore) {
      log(ignore);
    }
  }

  private static void log(Exception e) {
    //everything's gonna be alright
    //e.printStackTrace();
  }

  @Override
  public UIDefaults getDefaults() {
    try {
      final Method superMethod = base.getClass().getDeclaredMethod("getDefaults");
      superMethod.setAccessible(true);
      final UIDefaults defaults = (UIDefaults)superMethod.invoke(base);
      LafManagerImpl.initInputMapDefaults(defaults);
      initIdeaDefaults(defaults);
      return defaults;
    }
    catch (Exception ignore) {
      log(ignore);
    }
    return super.getDefaults();
  }

  private void call(String method) {
    try {
      final Method superMethod = base.getClass().getDeclaredMethod(method);
      superMethod.setAccessible(true);
      superMethod.invoke(base);
    }
    catch (Exception ignore) {
      log(ignore);
    }
  }

  public void initComponentDefaults(UIDefaults defaults) {
    callInit("initComponentDefaults", defaults);
  }

  @SuppressWarnings({"HardCodedStringLiteral"})
  static void initIdeaDefaults(UIDefaults defaults) {
    try {
      final Properties properties = new Properties();
      final InputStream stream = IdeaDarkLaf.class.getResourceAsStream("darcula_mac.properties");
      properties.load(stream);
      for (String key : properties.stringPropertyNames()) {
        final String value = properties.getProperty(key);
        if (key.endsWith("Insets")) {
          final List<String> numbers = StringUtil.split(value, ",");
          defaults.put(key, new InsetsUIResource(Integer.parseInt(numbers.get(0)),
                                                 Integer.parseInt(numbers.get(1)),
                                                 Integer.parseInt(numbers.get(2)),
                                                 Integer.parseInt(numbers.get(3))));
        } else {
        final Color color = ColorUtil.fromHex(value, null);
        final Integer invVal = getInteger(value);

        if (color != null) {
          defaults.put(key, new ColorUIResource(color));
        } else if (invVal != null) {
          defaults.put(key, invVal);
        } else {
          defaults.put(key, value);
        }
        }
      }
    }
    catch (IOException e) {
      log(e);
    }
    //defaults.put("TitledBorder.titleColor", IdeaBlueMetalTheme.primary1);
    //ColorUIResource col = new ColorUIResource(0, 0, 0);
    //defaults.put("ScrollBar.background", col);
    //defaults.put("ScrollBar.track", col);
    //
    ////Border scrollPaneBorder = new BorderUIResource(new BegBorders.ScrollPaneBorder());
    ////defaults.put("ScrollPane.border", scrollPaneBorder);
    //ColorUIResource defaultBackground = new ColorUIResource(68, 68, 68);
    //ColorUIResource defaultForeground = new ColorUIResource(255, 255, 255);
    //final ColorUIResource selectionBackground = new ColorUIResource(44, 44, 44);
    //defaults.put("Tree.background", defaultBackground);
    //defaults.put("List.background", defaultBackground);
    //defaults.put("Table.background", defaultBackground);
    //defaults.put("Tree.foreground", defaultForeground);
    //defaults.put("Tree.textForeground", defaultForeground);
    //defaults.put("Tree.selectionBackground", selectionBackground);
    //defaults.put("Tree.selectionForeground", defaultForeground);
    //
    //defaults.put("List.foreground", defaultForeground);
    //defaults.put("Table.foreground", defaultForeground);
    //
    //defaults.put("control", new ColorUIResource(44, 44, 44));
    //
    //defaults.put("Panel.background", new ColorUIResource(44, 44, 44));
    //defaults.put("Panel.foreground", new ColorUIResource(255, 255, 255));
    //defaults.put("Menu.selectionBackground", new ColorUIResource(190, 190, 190));
    //defaults.put("Menu.background", new ColorUIResource(68, 68, 68));
    //defaults.put("MenuItem.background", new ColorUIResource(68, 68, 68));
    //defaults.put("Menu.foreground", new ColorUIResource(255, 255, 255));
    //defaults.put("MenuItem.foreground", new ColorUIResource(255, 255, 255));
    ////defaults.put("ScrollPaneUI", BegScrollPaneUI.class.getName());
    //
    //defaults.put("TabbedPane.tabInsets", new Insets(0, 4, 0, 4));
    //defaults.put("ToolTip.background", new ColorUIResource(0, 14, 0));
    //defaults.put("ToolTip.border", new ColoredSideBorder(Color.gray, Color.gray, Color.black, Color.black, 1));
    //defaults.put("Tree.ancestorInputMap", null);
    //defaults.put("FileView.directoryIcon", AllIcons.Nodes.Folder);
    //defaults.put("FileChooser.upFolderIcon", AllIcons.Nodes.UpFolder);
    //defaults.put("FileChooser.newFolderIcon", AllIcons.Nodes.NewFolder);
    //defaults.put("FileChooser.homeFolderIcon", AllIcons.Nodes.HomeFolder);
    //defaults.put("OptionPane.errorIcon", AllIcons.General.ErrorDialog);
    //defaults.put("OptionPane.informationIcon", AllIcons.General.InformationDialog);
    //defaults.put("OptionPane.warningIcon", AllIcons.General.WarningDialog);
    //defaults.put("OptionPane.questionIcon", AllIcons.General.QuestionDialog);
    //defaults.put("Tree.expandedIcon", WindowsTreeUI.ExpandedIcon.createExpandedIcon());
    //defaults.put("Tree.collapsedIcon", WindowsTreeUI.CollapsedIcon.createCollapsedIcon());
    defaults.put("Table.ancestorInputMap", new UIDefaults.LazyInputMap(new Object[] {
      "ctrl C", "copy",
      "ctrl V", "paste",
      "ctrl X", "cut",
      "COPY", "copy",
      "PASTE", "paste",
      "CUT", "cut",
      "control INSERT", "copy",
      "shift INSERT", "paste",
      "shift DELETE", "cut",
      "RIGHT", "selectNextColumn",
      "KP_RIGHT", "selectNextColumn",
      "LEFT", "selectPreviousColumn",
      "KP_LEFT", "selectPreviousColumn",
      "DOWN", "selectNextRow",
      "KP_DOWN", "selectNextRow",
      "UP", "selectPreviousRow",
      "KP_UP", "selectPreviousRow",
      "shift RIGHT", "selectNextColumnExtendSelection",
      "shift KP_RIGHT", "selectNextColumnExtendSelection",
      "shift LEFT", "selectPreviousColumnExtendSelection",
      "shift KP_LEFT", "selectPreviousColumnExtendSelection",
      "shift DOWN", "selectNextRowExtendSelection",
      "shift KP_DOWN", "selectNextRowExtendSelection",
      "shift UP", "selectPreviousRowExtendSelection",
      "shift KP_UP", "selectPreviousRowExtendSelection",
      "PAGE_UP", "scrollUpChangeSelection",
      "PAGE_DOWN", "scrollDownChangeSelection",
      "HOME", "selectFirstColumn",
      "END", "selectLastColumn",
      "shift PAGE_UP", "scrollUpExtendSelection",
      "shift PAGE_DOWN", "scrollDownExtendSelection",
      "shift HOME", "selectFirstColumnExtendSelection",
      "shift END", "selectLastColumnExtendSelection",
      "ctrl PAGE_UP", "scrollLeftChangeSelection",
      "ctrl PAGE_DOWN", "scrollRightChangeSelection",
      "ctrl HOME", "selectFirstRow",
      "ctrl END", "selectLastRow",
      "ctrl shift PAGE_UP", "scrollRightExtendSelection",
      "ctrl shift PAGE_DOWN", "scrollLeftExtendSelection",
      "ctrl shift HOME", "selectFirstRowExtendSelection",
      "ctrl shift END", "selectLastRowExtendSelection",
      "TAB", "selectNextColumnCell",
      "shift TAB", "selectPreviousColumnCell",
      //"ENTER", "selectNextRowCell",
      "shift ENTER", "selectPreviousRowCell",
      "ctrl A", "selectAll",
      //"ESCAPE", "cancel",
      "F2", "startEditing"
    }));
  }

  private static Integer getInteger(String value) {
    try {
      return Integer.parseInt(value);
    }
    catch (NumberFormatException e) {
      return null;
    }
  }


  @Override
  public String getName() {
    return "Darcula";
  }

  @Override
  public String getID() {
    return getName();
  }

  @Override
  public String getDescription() {
    return "IntelliJ Dark Look and Feel";
  }

  @Override
  public boolean isNativeLookAndFeel() {
    return true;
  }

  @Override
  public boolean isSupportedLookAndFeel() {
    return true;
  }

  @Override
  protected void initSystemColorDefaults(UIDefaults defaults) {
    callInit("initSystemColorDefaults", defaults);
  }

  @Override
  protected void initClassDefaults(UIDefaults defaults) {
    callInit("initClassDefaults", defaults);
  }

  @Override
  public void initialize() {
    call("initialize");
  }

  @Override
  public void uninitialize() {
    call("uninitialize");
  }

  @Override
  protected void loadSystemColors(UIDefaults defaults, String[] systemColors, boolean useNative) {
    try {
      final Method superMethod = base.getClass().getDeclaredMethod("loadSystemColors",
                                                                   UIDefaults.class,
                                                                   String[].class,
                                                                   boolean.class);
      superMethod.setAccessible(true);
      superMethod.invoke(base, defaults, systemColors, useNative);
    }
    catch (Exception ignore) {
      log(ignore);
    }
  }


  @Override
  public boolean getSupportsWindowDecorations() {
    return true;
  }
}
