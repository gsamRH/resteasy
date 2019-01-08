package org.jboss.resteasy.client.exception;

/**
 * @author <a href="ron.sigal@jboss.com">Ron Sigal</a>
 * @version $Revision: 1.1 $
 *
 * Copyright Jul 28, 2012
 */
public class ResteasyMalformedCookieException extends ResteasyProtocolException
{
   private static final long serialVersionUID = -5711578608757689465L;

   public ResteasyMalformedCookieException()
   {
   }

   public ResteasyMalformedCookieException(final String message)
   {
      super(message);
   }

   public ResteasyMalformedCookieException(final String message, final Throwable cause)
   {
      super(message, cause);
   }

   public ResteasyMalformedCookieException(final Throwable cause)
   {
      super(cause);
   }
}