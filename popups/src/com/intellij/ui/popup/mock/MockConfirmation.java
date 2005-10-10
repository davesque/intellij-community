package com.intellij.ui.popup.mock;

import com.intellij.ui.popup.list.ListPopupImpl;
import com.intellij.openapi.ui.popup.PopupStep;

import java.awt.*;

/**
 * User: Sergey.Vasiliev
 * Date: Nov 21, 2004
 */
public class MockConfirmation extends ListPopupImpl {
  String myOnYesText;
  public MockConfirmation(PopupStep aStep, String onYesText) {
    super(aStep);
    myOnYesText = onYesText;
  }

  public void showInCenterOf(Component aContainer) {
    getStep().onChosen(myOnYesText);
  }

  public void showUnderneathOf(Component aComponent) {
    getStep().onChosen(myOnYesText);
  }
}
