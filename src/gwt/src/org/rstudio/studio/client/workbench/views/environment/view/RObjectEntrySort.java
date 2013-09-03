/*
 * RObjectEntrySort.java
 *
 * Copyright (C) 2009-12 by RStudio, Inc.
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

package org.rstudio.studio.client.workbench.views.environment.view;

import java.util.Comparator;

class RObjectEntrySort implements Comparator<RObjectEntry>
{
   public RObjectEntrySort()
   {
      sortType_ = SORT_AUTO;
      sortColumn_ = ObjectGridColumn.COLUMN_NAME;
   }
   
   public void setSortType(int sortType)
   {
      sortType_ = sortType;
   }
   
   public void setSortColumn(int sortColumn)
   {
      sortColumn_ = sortColumn;
   }

   public int compare(RObjectEntry first, RObjectEntry second)
   {
      int result = 0;
      if (sortType_ == SORT_AUTO)
      {
         result = first.getCategory() - second.getCategory();
         if (result == 0)
         {
            result = localeCompare(first.rObject.getName(), 
                                   second.rObject.getName());
         }
      }
      else if (sortType_ == SORT_COLUMN)
      {
         switch (sortColumn_)
         {
         case ObjectGridColumn.COLUMN_NAME:
            result = localeCompare(first.rObject.getName(),
                                   second.rObject.getName());
            break;
         case ObjectGridColumn.COLUMN_TYPE:
            result = localeCompare(first.rObject.getType(),
                                   second.rObject.getType());
            break;
         case ObjectGridColumn.COLUMN_LENGTH:
            result = first.rObject.getLength() - second.rObject.getLength();
            break;
         case ObjectGridColumn.COLUMN_SIZE:
            result = first.rObject.getSize() - second.rObject.getSize();
            break;
         case ObjectGridColumn.COLUMN_VALUE:
            result = localeCompare(first.rObject.getValue(), 
                                   second.rObject.getValue());
            break;
         }
      }
      return result;
   }

   private native int localeCompare(String first, String second) /*-{
       return first.localeCompare(second);
   }-*/;
   
   public static final int SORT_AUTO = 0;
   public static final int SORT_COLUMN = 1;
   
   private int sortType_;
   private int sortColumn_;
}
