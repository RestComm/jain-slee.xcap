/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.restcomm.slee.resource.xcapclient;

import java.net.URI;

import org.restcomm.slee.resource.xcapclient.handler.AsyncDeleteHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncDeleteIfMatchHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncDeleteIfNoneMatchHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncGetHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncPutByteArrayContentHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncPutIfMatchByteArrayContentHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncPutIfMatchStringContentHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncPutIfNoneMatchByteArrayContentHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncPutIfNoneMatchStringContentHandler;
import org.restcomm.slee.resource.xcapclient.handler.AsyncPutStringContentHandler;
import org.restcomm.xcap.client.auth.Credentials;
import org.restcomm.xcap.client.header.Header;

public class AsyncActivityImpl implements AsyncActivity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final transient XCAPClientResourceAdaptor ra;
	private final XCAPResourceAdaptorActivityHandle handle;

	/**
	 * 
	 * @param ra
	 * @param handle
	 */
	public AsyncActivityImpl(XCAPClientResourceAdaptor ra,
			XCAPResourceAdaptorActivityHandle handle) {
		this.handle = handle;
		this.ra = ra;
	}

	/**
	 * 
	 * @return
	 */
	public XCAPClientResourceAdaptor getRA() {
		return ra;
	}

	/**
	 * 
	 * @return
	 */
	public XCAPResourceAdaptorActivityHandle getHandle() {
		return handle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AsyncActivity#endActivity()
	 */
	public void endActivity() {
		ra.endActivity(handle);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o != null && o.getClass() == this.getClass()) {
			return ((AsyncActivityImpl) o).handle.equals(this.handle);
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return handle.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#delete(java.net.URI,
	 * Header[],
	 * Credentials)
	 */
	public void delete(URI uri, Header[] additionalRequestHeaders,
			Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncDeleteHandler(ra, handle, uri,
						additionalRequestHeaders, credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#deleteIfMatch(java
	 * .net.URI, java.lang.String, Header[],
	 * Credentials)
	 */
	public void deleteIfMatch(URI uri, String eTag,
			Header[] additionalRequestHeaders, Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncDeleteIfMatchHandler(ra, handle, uri, eTag,
						additionalRequestHeaders, credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#deleteIfNoneMatch
	 * (java.net.URI, java.lang.String,
	 * Header[],
	 * Credentials)
	 */
	public void deleteIfNoneMatch(URI uri, String eTag,
			Header[] additionalRequestHeaders, Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncDeleteIfNoneMatchHandler(ra, handle, uri, eTag,
						additionalRequestHeaders, credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#get(java.net.URI,
	 * Header[],
	 * Credentials)
	 */
	public void get(URI uri, Header[] additionalRequestHeaders,
			Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncGetHandler(ra, handle, uri, additionalRequestHeaders,
						credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#put(java.net.URI,
	 * java.lang.String, java.lang.String,
	 * Header[],
	 * Credentials)
	 */
	public void put(URI uri, String mimetype, String content,
			Header[] additionalRequestHeaders, Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncPutStringContentHandler(ra, handle, uri, mimetype,
						content, additionalRequestHeaders, credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#put(java.net.URI,
	 * java.lang.String, byte[], Header[],
	 * Credentials)
	 */
	public void put(URI uri, String mimetype, byte[] content,
			Header[] additionalRequestHeaders, Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncPutByteArrayContentHandler(ra, handle, uri, mimetype,
						content, additionalRequestHeaders, credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#putIfMatch(java.
	 * net.URI, java.lang.String, java.lang.String, java.lang.String,
	 * Header[],
	 * Credentials)
	 */
	public void putIfMatch(URI uri, String eTag, String mimetype,
			String content, Header[] additionalRequestHeaders,
			Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncPutIfMatchStringContentHandler(ra, handle, uri, eTag,
						mimetype, content, additionalRequestHeaders,
						credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#putIfMatch(java.
	 * net.URI, java.lang.String, java.lang.String, byte[],
	 * Header[],
	 * Credentials)
	 */
	public void putIfMatch(URI uri, String eTag, String mimetype,
			byte[] content, Header[] additionalRequestHeaders,
			Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncPutIfMatchByteArrayContentHandler(ra, handle, uri,
						eTag, mimetype, content, additionalRequestHeaders,
						credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#putIfNoneMatch(java
	 * .net.URI, java.lang.String, java.lang.String, java.lang.String,
	 * Header[],
	 * Credentials)
	 */
	public void putIfNoneMatch(URI uri, String eTag, String mimetype,
			String content, Header[] additionalRequestHeaders,
			Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncPutIfNoneMatchStringContentHandler(ra, handle, uri,
						eTag, mimetype, content, additionalRequestHeaders,
						credentials));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AsyncActivity#putIfNoneMatch(java
	 * .net.URI, java.lang.String, java.lang.String, byte[],
	 * Header[],
	 * Credentials)
	 */
	public void putIfNoneMatch(URI uri, String eTag, String mimetype,
			byte[] content, Header[] additionalRequestHeaders,
			Credentials credentials) {
		ra.getExecutorService().execute(
				new AsyncPutIfNoneMatchByteArrayContentHandler(ra, handle, uri,
						eTag, mimetype, content, additionalRequestHeaders,
						credentials));
	}

}
