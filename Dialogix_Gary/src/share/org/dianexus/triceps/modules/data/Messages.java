package org.dianexus.triceps.modules.data;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;

public class Messages {
  static Logger logger = Logger.getLogger(Messages.class);
	private static final String BUNDLE_NAME = "org.dianexus.triceps.modules.data.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			logger.error("Missing resource bundle" + '!' + key + '!', e);
			return '!' + key + '!';
		}
	}
}
