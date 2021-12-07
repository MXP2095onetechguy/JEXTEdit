package MXPSQL.JEXTEdit;

/*
JEXTEdit, text editor with web browser
Copyright (C) 2021 MXPSQL

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

import jakarta.xml.bind.annotation.*;

@XmlRootElement
public class Views {

    String tabsAlign;
    String documentSelector;
    String toolStrip;
    String statusStrip;
    String lookAndFeel;

    public String getTabsAlignment() {
        return tabsAlign;
    }

    @XmlElement
    public void setTabsAlignment(String tabsAlign) {
        this.tabsAlign = tabsAlign;
    }

    public String getDocumentSelector() {
        return documentSelector;
    }

    @XmlElement
    public void setDocumentSelector(String documentSelector) {
        this.documentSelector = documentSelector;
    }

    public String getToolStrip() {
        return toolStrip;
    }

    @XmlElement
    public void setToolStrip(String toolStrip) {
        this.toolStrip = toolStrip;
    }

    public String getStatusStrip() {
        return statusStrip;
    }

    @XmlElement
    public void setStatusStrip(String statusStrip) {
        this.statusStrip = statusStrip;
    }

    public String getLookAndFeel() {
        return lookAndFeel;
    }

    @XmlElement
    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

}
