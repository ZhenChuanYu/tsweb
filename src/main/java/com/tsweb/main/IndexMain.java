package com.tsweb.main;

import java.util.List;
import org.apache.log4j.Logger;
import com.tsweb.bean.BaseObject;
import com.tsweb.jdbc.dao.BaseDao;
import com.tsweb.lucene.Indexer;

public class IndexMain {
	private static Logger log = Logger.getLogger(IndexMain.class);

	public static void main(String[] args) {

		do {
			log.info("start index ...");

			List<BaseObject> objs = BaseDao.getIndexedObject();
			log.info("total objs: " + objs.size());

			Indexer indexer = new Indexer();
			indexer.index(objs);

			//update index mark in database
			log.info("done ...");

			try {
				log.info("sleep 5000ms");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (false);
	}
}
