package com.tsweb.lucene;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.tsweb.bean.BaseObject;
import com.tsweb.config.Configure;

public class Writer {
	private static final String indexDir = Configure.getProperty("index_path");
	private IndexWriter writer;
	private Directory dir;
	private Analyzer analyzer;
	private IndexWriterConfig config;

	public Writer() {
		try {
			dir = new SimpleFSDirectory(new File(indexDir));
			analyzer = new StandardAnalyzer(Version.LUCENE_33);
			config = new IndexWriterConfig(Version.LUCENE_33, analyzer);
			// config.setOpenMode(IndexWriterConfig.OpenMode.APPEND);
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(dir, config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writer(BaseObject obj) {
		try {
			Document doc = new Document();
			doc.add(new Field("id", String.valueOf(obj.getId()), Field.Store.YES, Field.Index.NO));
			doc.add(new Field("name", obj.getName(), Field.Store.YES, Field.Index.ANALYZED));
			doc.add(new Field("description", obj.getDescription(), Field.Store.YES, Field.Index.ANALYZED));
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (writer != null) {
			try {
				writer.optimize();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
