@Value.Style(typeImmutable = "*", builder = "new",
        additionalJsonAnnotations = {XmlRootElement.class, XmlJavaTypeAdapter.class})
@XmlJavaTypeAdapters(
        @XmlJavaTypeAdapter(value = DataAdapter.class, type = Data.class))
package com.oracle.homework.core.domain;

import com.oracle.homework.core.DataAdapter;
import org.immutables.value.Value;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;