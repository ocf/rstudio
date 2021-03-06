/*
 * SendToTerminalEvent.java
 *
 * Copyright (C) 2009-17 by RStudio, Inc.
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.workbench.views.terminal.events;

import org.rstudio.core.client.js.JavaScriptSerializable;
import org.rstudio.studio.client.application.events.CrossWindowEvent;
import org.rstudio.studio.client.workbench.views.terminal.events.SendToTerminalEvent.Handler;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;

@JavaScriptSerializable
public class SendToTerminalEvent extends CrossWindowEvent<Handler>
{
   public interface Handler extends EventHandler
   {
      /**
       * Output text to a terminal, specified by terminal name. If given
       * name does not exist, create it. If name is NULL or empty, output
       * to currently active terminal (if any). If no terminal is active, then
       * open one.
       * @param event contains string data typed by the user
       */
      void onSendToTerminal(SendToTerminalEvent event);
   }

   public static class Data extends JavaScriptObject
   {
      protected Data() {}
      
      public final native String getText() /*-{ return this["text"]; }-*/;
      public final native String getId() /*-{ return this["id"]; }-*/;
   }
  
   public SendToTerminalEvent()
   {
   }

   public SendToTerminalEvent(Data data)
   {
      this(data.getText(), data.getId());
   }
   
   public SendToTerminalEvent(String text, 
                              String id)
   {
      text_ = text;
      id_ = id;
   }

   public String getText()
   {
      return text_;
   }
   
   public String getId()
   {
      return id_;
   }

   @Override
   public Type<Handler> getAssociatedType()
   {
      return TYPE;
   }

   @Override
   protected void dispatch(Handler sendToTerminalHandler)
   {
      sendToTerminalHandler.onSendToTerminal(this);
   }

   private String text_;
   private String id_;

   public static final Type<Handler> TYPE = new Type<Handler>();
}
