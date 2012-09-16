/**
 * The main package.
 * <hr/>
 * <strong>lightXml - A lightweight DOM-like XML notation wrapper</strong><br/>
 * Copyright (C) 2009  Thomas Boqvist, Digit Man<br/><br/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.<br/><br/>
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.<br/><br/>
 *
 * You should have received a copy of the 
 * <a href="http://www.gnu.org/licenses/">GNU General Public License</a>
 * along with this program.  If not, see
 * <a href="http://www.gnu.org/licenses/">GNU licenses</a>
 * <hr/>
 * This package contains the main part of the classes. The creation of nodes
 * is handled by NodeFactory. <em>Note that lightXml is not fully DOM compliant.</em>
 * The purpose is to handle the 90% of all cases which does not need
 * a complete DOM implementation.<br/>
 * Note that use of namespaces is not supported so far, and that tag attributes
 * are <strong>not</strong> considered a tree node.
 */

package se.digitman.lightxml;