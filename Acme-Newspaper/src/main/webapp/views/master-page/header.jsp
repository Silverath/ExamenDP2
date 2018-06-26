<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Newspaper Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>		
					<li><a href="administrator/listTabooWord.do"><spring:message code="master.page.administrator.tabooWords" /></a></li>	
					<li><a href="administrator/listTabooArticles.do"><spring:message code="master.page.administrator.tabooArticles" /></a></li>		
					<li><a href="administrator/listTabooNewspapers.do"><spring:message code="master.page.administrator.tabooNewspapers" /></a></li>		
					<li><a href="administrator/listTabooChirps.do"><spring:message code="master.page.administrator.tabooChirps" /></a></li>		
					<li><a href="advertisement/listTaboo.do"><spring:message code="master.page.advertisement.taboo" /></a></li>		
				</ul>
				
			</li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"><spring:message	code="master.page.subscription" /></a>
			
				<ul>
					<li class="arrow"></li>
					<li><a href="subscription/list.do"><spring:message code="master.page.subscription.list" /></a></li>
					<li><a href="subscription/create.do"><spring:message code="master.page.subscription.create" /></a></li>		
					<li><a href="subscription/subscribeVolume.do"><spring:message code="master.page.subscription.volume.create" /></a></li>				
				</ul>
			</li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('AGENT')">
			<li><a class="fNiv"><spring:message	code="master.page.advertisement" /></a>
			
				<ul>
					<li class="arrow"></li>
					<li><a href="advertisement/create.do"><spring:message code="master.page.advertisement.create" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
				<li><a class="fNiv" href="actor/register.do"><spring:message
						code="master.page.actor.register" /></a></li>

		</security:authorize>
		
		<security:authorize access="permitAll">
			<li><a class="fNiv"><spring:message	code="master.page.user" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="user/list.do"><spring:message code="master.page.user.list" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.audits" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="audit/list.do"><spring:message code="master.page.audits.list" /></a></li>
					<li><a href="audit/listMine.do"><spring:message code="master.page.audits.listMine" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.newspaper" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="newspaper/search.do"><spring:message code="master.page.search.newspaper" /></a></li>
					<li><a href="newspaper/list.do"><spring:message code="master.page.newspaper.list" /></a></li>
					<li><a href="volume/list.do"><spring:message code="master.page.volume.list" /></a></li>
					<security:authorize access="hasRole('USER')">
					<li><a href="newspaper/listPublish.do"><spring:message code="master.page.newspaper.listPublish" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('AGENT')">
					<li><a href="newspaper/listAdvertisements.do"><spring:message code="master.page.newspaper.listAdvertisements" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('AGENT')">
					<li><a href="newspaper/listWithoutAdvertisements.do"><spring:message code="master.page.newspaper.listWithoutAdvertisements" /></a></li>
					</security:authorize>
				</ul>
			</li>

			<li><a class="fNiv"><spring:message code="master.page.article" /></a>
		<ul>
					<li class="arrow"></li>
					<li><a href="article/search.do"><spring:message code="master.page.search.article" /></a></li>
					<li><a href="article/list.do"><spring:message code="master.page.article.list" /></a></li>
										
				</ul>
			</li>			
			
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"><spring:message	code="master.page.volume" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="volume/list.do"><spring:message code="master.page.volume.list" /></a></li>
					<li><a href="volume/create.do"><spring:message code="master.page.volume.create" /></a></li>										
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.chirp" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chirp/list.do"><spring:message code="master.page.chirp.list" /></a></li>
					<li><a href="chirp/listChirpsPostedByMyFollows.do"><spring:message code="master.page.chirp.listChirpsPostedByMyFollows" /></a></li>										
				</ul>
			</li>

		</security:authorize>
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.chirp" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="chirp/list.do"><spring:message code="master.page.chirp.list" /></a></li>
				</ul>
			</li>
			
			<li><a class="fNiv"><spring:message	code="master.page.advertisement" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="advertisement/list.do"><spring:message code="master.page.advertisement.list" /></a></li>
				</ul>
			</li>

		</security:authorize>

		<security:authorize access="hasRole('USER')">
		
					<li><a class="fNiv"><spring:message
						code="master.page.follow" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="follow/listUserFollows.do"><spring:message code="master.page.followsList" /></a></li>
					<li><a href="follow/listUserFollowers.do"><spring:message code="master.page.followersList" /></a></li>
				</ul>
			</li>
				
			</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
						<li><a class="fNiv"> <spring:message
						code="master.page.messages" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="message/list.do"><spring:message
								code="master.page.messages.my" /></a></li>
		<security:authorize access="hasRole('ADMIN')">
					
					<li><a href="message/createBroadcast.do"><spring:message
							code="master.page.messages.broadcast" /></a></li>
					</security:authorize>
			
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

