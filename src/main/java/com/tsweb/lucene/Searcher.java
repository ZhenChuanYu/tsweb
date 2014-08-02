package com.tsweb.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import com.tsweb.bean.SearchReuslt;
import com.tsweb.config.Configure;

public class Searcher {

	private String indexDir;
	private Directory dir;
	private IndexSearcher indexSearch;
	private String[] fields = { "name" };

	public Searcher() {
		indexDir = Configure.getProperty("index_path");
		try {
			dir = new SimpleFSDirectory(new File(indexDir));
			indexSearch = new IndexSearcher(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<SearchReuslt> query(String word, int page, int limit) {
		List<SearchReuslt> results = new ArrayList<SearchReuslt>();
		try {
			MultiFieldQueryParser multiQuery = new MultiFieldQueryParser(Version.LUCENE_30, fields, new StandardAnalyzer(Version.LUCENE_30));
			multiQuery.setDefaultOperator(QueryParser.Operator.AND);
			Query query = multiQuery.parse(word);
			TopDocs hits = indexSearch.search(query, 1000);

			int start = (page - 1) * limit;
			int end   = (start + limit) - 1;

			if (end > hits.scoreDocs.length) {
				end = hits.scoreDocs.length;
			}

			if (start > hits.scoreDocs.length) {
				start = hits.scoreDocs.length;
			}

			// 2, 20

			for (int i = start; i < end; i++) {
				ScoreDoc sdoc = hits.scoreDocs[i];
				Document doc = indexSearch.doc(sdoc.doc);

				SearchReuslt result = new SearchReuslt();
				result.setId(Integer.parseInt(doc.get("id")));
				result.setName(doc.get("name"));
				result.setDescription(doc.get("description"));
				results.add(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (indexSearch != null) {
					indexSearch.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
