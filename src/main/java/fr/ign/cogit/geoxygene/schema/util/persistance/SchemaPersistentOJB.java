/*
 * This file is part of the GeOxygene project source files. GeOxygene aims at
 * providing an open framework which implements OGC/ISO specifications for the
 * development and deployment of geographic (GIS) applications. It is a open
 * source contribution of the COGIT laboratory at the Institut Géographique
 * National (the French National Mapping Agency). See:
 * http://oxygene-project.sourceforge.net Copyright (C) 2005 Institut
 * Géographique National This library is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the License,
 * or any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library (see file
 * LICENSE if present); if not, write to the Free Software Foundation, Inc., 59
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package fr.ign.cogit.geoxygene.schema.util.persistance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.ign.cogit.geoxygene.datatools.Geodatabase;
import fr.ign.cogit.geoxygene.feature.DataSet;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.AssociationRole;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.AssociationType;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.AttributeType;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.FeatureAttributeValue;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.FeatureType;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.InheritanceRelation;
import fr.ign.cogit.geoxygene.schema.schemaConceptuelISOJeu.SchemaConceptuelJeu;

/**
 * Gere la persistence des elements de schema conceptuel
 * @author Nathalie Abadie
 */

public abstract class SchemaPersistentOJB {

  /** méthode qui supprime définitivement un objet de la base */
  public void deletePersistent() {
    // Initialisation d'une connexion avec le SGBD et le mapping
    Geodatabase bd = DataSet.db;
    // Si ma connexion est définie et ma base ouverte...
    if (bd != null && bd.isOpen()) {
      // Suppression de l'objet persistent
      bd.deletePersistent(this);
    } else // L'objet ne pourra être effacé: envoi d'un message d'erreur
    {
      System.err
          .println("Attention : effacement d'un objet persistant alors qu'aucune "
              + "transaction n'est en cours. L'objet n'a pas été effacé de la base.");
    }
  }// Fin de la méthode deletePersistent

  /** méthode qui rend un objet persistent */
  public void makePersistent() {
    // Initialisation d'une connexion avec le SGBD et le mapping
    Geodatabase bd = DataSet.db;
    // Sauvegarde de l'objet dans la base
    if (bd != null && bd.isOpen()) {
      bd.makePersistent(this);
    } else {
      System.out.println("problèmes de connexion à la BD!");
    }
  }// Fin de la méthode makePersistent

  /**
   * méthode qui efface un SchemaConceptuelJeu de la base Oracle
   */
  public static void deleteSchema(SchemaConceptuelJeu schema) {

    if (DataSet.db != null && DataSet.db.isOpen()) {

      // Rassemblement de toutes les informations sur le schéma
      List<FeatureType> ftListLocal = new ArrayList<FeatureType>();
      List<FeatureType> ftList;
      List<AttributeType> attList;
      List<FeatureAttributeValue> valList;
      List<AssociationType> assoList;
      List<AssociationRole> roleList;
      List<InheritanceRelation> heritList;

      // Suppression de tous les éléments du schéma

      attList = schema.getFeatureAttributes();
      Iterator<AttributeType> iTatt = attList.iterator();
      while (iTatt.hasNext()) {
        AttributeType att = iTatt.next();
        DataSet.db.deletePersistent(att);
      }

      valList = schema.getFeatureAttributeValues();
      Iterator<FeatureAttributeValue> iTval = valList.iterator();
      while (iTval.hasNext()) {
        FeatureAttributeValue val = iTval.next();
        DataSet.db.deletePersistent(val);
      }

      assoList = schema.getFeatureAssociations();
      Iterator<AssociationType> iTasso = assoList.iterator();
      while (iTasso.hasNext()) {
        AssociationType asso = iTasso.next();
        DataSet.db.deletePersistent(asso);
      }

      roleList = schema.getAssociationRoles();
      Iterator<AssociationRole> iTrole = roleList.iterator();
      while (iTrole.hasNext()) {
        AssociationRole role = iTrole.next();
        DataSet.db.deletePersistent(role);
      }

      heritList = schema.getInheritance();
      Iterator<InheritanceRelation> iTherit = heritList.iterator();
      while (iTherit.hasNext()) {
        InheritanceRelation herit = iTherit.next();
        DataSet.db.deletePersistent(herit);
      }

      ftList = schema.getFeatureTypes();
      Iterator<FeatureType> iTft = ftList.iterator();
      while (iTft.hasNext()) {
        FeatureType ft = iTft.next();
        DataSet.db.deletePersistent(ft);
        ftListLocal.add(ft);
      }

      // Suppression des objets en mémoire
      Iterator<FeatureType> iTftloc = ftListLocal.iterator();
      while (iTftloc.hasNext()) {
        FeatureType ft = iTftloc.next();
        schema.removeFeatureType(ft);
      }

      // Suppression du schéma lui même
      DataSet.db.deletePersistent(schema);
    } else {
      {
        System.err
            .println("Attention : effacement d'un objet persistant alors qu'aucune "
                + "transaction n'est en cours. L'objet n'a pas été effacé de la base.");
      }
    }
  }

  /**
   * Efface un SchemaConceptuelJeu de la base Oracle
   */
  public static void deleteSchema(
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.SchemaConceptuelProduit schema) {

    // Rassemblement de toutes les informations sur le schéma
    List<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureType> ftListLocal = new ArrayList<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureType>();
    List<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureType> ftList;
    List<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AttributeType> attList;
    List<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureAttributeValue> valList;
    List<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AssociationType> assoList;
    List<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AssociationRole> roleList;
    List<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.InheritanceRelation> heritList;

    // Suppression de tous les éléments du schéma

    attList = schema.getFeatureAttributes();
    Iterator<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AttributeType> iTatt = attList
        .iterator();
    while (iTatt.hasNext()) {
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AttributeType att = iTatt
          .next();
      DataSet.db.deletePersistent(att);
    }

    valList = schema.getFeatureAttributeValues();
    Iterator<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureAttributeValue> iTval = valList
        .iterator();
    while (iTval.hasNext()) {
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureAttributeValue val = iTval
          .next();
      DataSet.db.deletePersistent(val);
    }

    assoList = schema.getFeatureAssociations();
    Iterator<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AssociationType> iTasso = assoList
        .iterator();
    while (iTasso.hasNext()) {
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AssociationType asso = iTasso
          .next();
      DataSet.db.deletePersistent(asso);
    }

    roleList = schema.getAssociationRoles();
    Iterator<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AssociationRole> iTrole = roleList
        .iterator();
    while (iTrole.hasNext()) {
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.AssociationRole role = iTrole
          .next();
      DataSet.db.deletePersistent(role);
    }

    heritList = schema.getInheritance();
    Iterator<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.InheritanceRelation> iTherit = heritList
        .iterator();
    while (iTherit.hasNext()) {
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.InheritanceRelation herit = iTherit
          .next();
      DataSet.db.deletePersistent(herit);
    }

    ftList = schema.getFeatureTypes();
    Iterator<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureType> iTft = ftList
        .iterator();
    while (iTft.hasNext()) {
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureType ft = iTft
          .next();
      DataSet.db.deletePersistent(ft);
      ftListLocal.add(ft);
    }

    // Suppression des objets en mémoire
    Iterator<fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureType> iTftloc = ftListLocal
        .iterator();
    while (iTftloc.hasNext()) {
      fr.ign.cogit.geoxygene.schema.schemaConceptuelISOProduit.FeatureType ft = iTftloc
          .next();
      schema.removeFeatureType(ft);
    }

    // Suppression du schéma lui même
    DataSet.db.deletePersistent(schema);
  }

}
