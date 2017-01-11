package com.tnc.template.common.di;


/**
 * Interface help plus subComponent
 * to application component
 */
public interface SuperComponent {
  <T> T plus(Object module);
}
