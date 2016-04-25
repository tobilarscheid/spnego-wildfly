spnego-wildfly
==============

spnego alternative for wildfly, based on [spnego.sourceforge.net](http://spnego.sourceforge.net)

Installation
------------
- Add krb5.conf to JBOSS_HOME/bin

        [libdefaults]
	      default_tkt_enctypes = aes128-cts rc4-hmac des3-cbc-sha1 des-cbc-md5 des-cbc-crc
	      default_tgs_enctypes = aes128-cts rc4-hmac des3-cbc-sha1 des-cbc-md5 des-cbc-crc
	      permitted_enctypes   = aes128-cts rc4-hmac des3-cbc-sha1 des-cbc-md5 des-cbc-crc
	      dns_lookup_realm = false

        [realms]
             ...
        [domain_realm]
             ...

- Add system properties (standalone.xml or standalone.conf ...)

        <system-properties>
          <property name="sun.security.krb5.debug"   value="false"/>
          <property name="java.security.krb5.kdc"    value="kdc-FQN"/>
          <property name="java.security.krb5.realm"  value="DOMAIN.TLD"/>
          <!-- krb5.conf in JBOSS_HOME/bin or full path -->
          <property name="java.security.krb5.conf"   value="krb5.conf"/>
          <!-- using preauth without keytab -->
          <property name="spnego.preauth.username" value="principal@DOMAIN.TLD"/>
          <property name="spnego.preauth.password" value="secret"/>
        </system-properties>

	
- Add login modules to standalone.xml :
	
        <security-domain name="spnego-server">
          <authentication>
            <login-module code="com.sun.security.auth.module.Krb5LoginModule" flag="required">
              <module-option name="storeKey" value="true"/>
              <!--
              <module-option name="useKeyTab" value="true"/>
              <module-option name="keyTab"    value="path-to-ketytab"/>
              <module-option name="principal" value="keytab-principal"/>
              <module-option name="isInitiator" value="false"/>
              -->
            </login-module>
          </authentication>
        </security-domain>
        <security-domain name="spnego-roles" cache-type="default">
          <authentication>
            <login-module code="de.ctrlaltdel.SpnegoAckLoginModule" flag="required"/>
            <!-- simple UsersRoles or LDAP etc. -->
            <login-module code="UsersRoles" flag="sufficient">
              <module-option name="password-stacking" value="useFirstPass"/>
              <module-option name="usersProperties"   value="spnego-users.properties"/>
              <module-option name="rolesProperties"   value="spnego-roles.properties"/>
            </login-module>
          </authentication>
        </security-domain>
          
- Add spnego-auth as dependency to the webapp
     
        <dependency>
           <groupId>de.ctrlaltdel</groupId>
           <artifactId>spnego-auth</artifactId>
           <version>1.0</version>
        </dependency>
	

- And now - Rock'n'Roll !


*Published without warranty under Free-For-Beer license*