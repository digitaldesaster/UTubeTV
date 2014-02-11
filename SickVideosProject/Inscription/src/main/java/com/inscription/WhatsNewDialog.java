/*
 * (c) 2012 Martin van Zuilekom (http://martin.cubeactive.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.inscription;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

public class WhatsNewDialog extends ChangeLogDialog {
  private static final String WHATS_NEW_LAST_SHOWN = "whats_new_last_shown";

  public WhatsNewDialog(final Context context) {
    super(context);
  }

  private int getAppVersionCode() {
    try {
      final PackageInfo packageInfo = getContext().getPackageManager()
          .getPackageInfo(getContext().getPackageName(), 0);
      return packageInfo.versionCode;
    } catch (NameNotFoundException ignored) {
      return 0;
    }
  }

  public void forceShow() {
    show(getAppVersionCode());
  }

  @Override
  public void show() {
    final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
    final int versionShown = prefs.getInt(WHATS_NEW_LAST_SHOWN, 0);
    if (versionShown != getAppVersionCode()) {
      show(getAppVersionCode());

      final SharedPreferences.Editor edit = prefs.edit();
      edit.putInt(WHATS_NEW_LAST_SHOWN, getAppVersionCode());
      edit.commit();
    }
    if (mOnDismissListener != null) {
      mOnDismissListener.onDismiss(null);
    }
  }
}