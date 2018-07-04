package net.lenords.yama.internal.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author len0rd
 * @since 2018-06-21
 */
public class Crawler {

	private String name;
	private Set<Page> pages;

	public Crawler(String name) {
		this.name = name;
		this.pages = new HashSet<>();
	}

	public Crawler addPage(Page pageToAdd) {
		if (pages.stream().noneMatch(page -> page.getName().equals(pageToAdd.getName()))) {
			pages.add(pageToAdd);
		} else {
			System.out.println(
				"Could not add page: "
					+ pageToAdd.getName()
					+ " since the crawler already has a page by that name");
		}
		return this;
	}

	public Set<Page> getPages() {
		return pages;
	}

	public void setPages(Set<Page> pages) {
		this.pages = pages;
	}
}
