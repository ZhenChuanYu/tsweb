package com.tsweb.lucene;

import java.util.List;
import org.apache.log4j.Logger;
import com.tsweb.bean.BaseObject;

public class Indexer {
	private static Logger log = Logger.getLogger(Indexer.class);

	public void index(List<BaseObject> indexObjs) {
		Writer writer = new Writer();
		for (BaseObject obj : indexObjs) {
			writer.writer(obj);
		}
		writer.close();
	}
}