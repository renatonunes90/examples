package com.example.services.domain;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entities.domain.MaritalStatus;
import com.example.exception.ErrorMessageGenericException;
import com.example.repositories.domain.MaritalStatusRepository;
import com.example.services.domain.base.AbstractDomainService;

/**
 * Service responsible for searching and editing marital status through the repositories.
 * 
 * @author Renato M B Nunes
 */
@Service
@Transactional
public class MaritalStatusService extends AbstractDomainService< MaritalStatus >
{

   @Autowired
   private MaritalStatusRepository maritalStatusRepository;

   @Override
   protected JpaRepository< MaritalStatus, Long > getRepository()
   {
      return maritalStatusRepository;
   }

   @Override
   protected boolean isValidToDelete( MaritalStatus MaritalStatus )
   {
      StringBuilder msg = new StringBuilder( "" );

      if ( MaritalStatus.getMaritalStatusId() == null )
      {
         msg.append( getProperties().getMessage( "msg.error.domain.maritalStatus.maritalStatusId" ) );
      }

      if ( msg.length() > 0 )
      {
         throw new ErrorMessageGenericException( msg.toString() );
      }

      return true;
   }

   @Override
   protected boolean isValidToInsert( MaritalStatus maritalStatus )
   {
      StringBuilder msg = new StringBuilder( "" );

      if ( !alreadyExists( maritalStatus ) )
      {
         if ( maritalStatus.getMaritalStatusText() == null )
         {
            msg.append( getProperties().getMessage( "msg.error.domain.maritalStatus.maritalStatusText" ) );
         }

         if ( msg.length() > 0 )
         {
            throw new ErrorMessageGenericException( msg.toString() );
         }
      }
      return true;
   }

   @Override
   protected boolean isValidToUpdate( MaritalStatus maritalStatus )
   {
      StringBuilder msg = new StringBuilder( "" );

      if ( !alreadyExists( maritalStatus ) )
      {
         if ( maritalStatus.getMaritalStatusId() == null )
         {
            msg.append( getProperties().getMessage( "msg.error.domain.maritalStatus.maritalStatusId" ) );
         }
         if ( maritalStatus.getMaritalStatusText() == null )
         {
            msg.append( getProperties().getMessage( "msg.error.domain.maritalStatus.maritalStatusText" ) );
         }

         if ( msg.length() > 0 )
         {
            throw new ErrorMessageGenericException( msg.toString() );
         }
      }

      return true;
   }

   /**
    * Verifies if the marital status already exists in database.
    * 
    * @param maritalStatus
    * @return
    */
   private boolean alreadyExists( MaritalStatus maritalStatus )
   {

      Optional< MaritalStatus > found = maritalStatusRepository.findByMaritalStatusText( maritalStatus.getMaritalStatusText() );
      if ( found.isPresent() && found.get().getMaritalStatusId() != maritalStatus.getMaritalStatusId() )
      {
         throw new ErrorMessageGenericException( getProperties().getMessage( "msg.error.domain.maritalStatus.alreadyExists" ) );
      }

      return false;
   }

}
