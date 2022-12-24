import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { ILocal } from 'app/shared/model/local.model';
import { getEntities as getLocals } from 'app/entities/local/local.reducer';
import { IDossierAutre } from 'app/shared/model/dossier-autre.model';
import { getEntity, updateEntity, createEntity, reset } from './dossier-autre.reducer';

export const DossierAutreUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const documents = useAppSelector(state => state.document.entities);
  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const locals = useAppSelector(state => state.local.entities);
  const dossierAutreEntity = useAppSelector(state => state.dossierAutre.entity);
  const loading = useAppSelector(state => state.dossierAutre.loading);
  const updating = useAppSelector(state => state.dossierAutre.updating);
  const updateSuccess = useAppSelector(state => state.dossierAutre.updateSuccess);

  const handleClose = () => {
    navigate('/dossier-autre');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getDocuments({}));
    dispatch(getUtilisateurs({}));
    dispatch(getLocals({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateDepot = convertDateTimeToServer(values.dateDepot);
    values.dateEnvoi = convertDateTimeToServer(values.dateEnvoi);
    values.dateDerniereModif = convertDateTimeToServer(values.dateDerniereModif);

    const entity = {
      ...dossierAutreEntity,
      ...values,
      documentLists: mapIdList(values.documentLists),
      user: users.find(it => it.id.toString() === values.user.toString()),
      utilisateur: utilisateurs.find(it => it.id.toString() === values.utilisateur.toString()),
      local: locals.find(it => it.id.toString() === values.local.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateDepot: displayDefaultDateTime(),
          dateEnvoi: displayDefaultDateTime(),
          dateDerniereModif: displayDefaultDateTime(),
        }
      : {
          ...dossierAutreEntity,
          dateDepot: convertDateTimeFromServer(dossierAutreEntity.dateDepot),
          dateEnvoi: convertDateTimeFromServer(dossierAutreEntity.dateEnvoi),
          dateDerniereModif: convertDateTimeFromServer(dossierAutreEntity.dateDerniereModif),
          user: dossierAutreEntity?.user?.id,
          documentLists: dossierAutreEntity?.documentLists?.map(e => e.id.toString()),
          utilisateur: dossierAutreEntity?.utilisateur?.id,
          local: dossierAutreEntity?.local?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.dossierAutre.home.createOrEditLabel" data-cy="DossierAutreCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.dossierAutre.home.createOrEditLabel">Create or edit a DossierAutre</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="dossier-autre-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.numero')}
                id="dossier-autre-numero"
                name="numero"
                data-cy="numero"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.numeroEnvoi')}
                id="dossier-autre-numeroEnvoi"
                name="numeroEnvoi"
                data-cy="numeroEnvoi"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.dateDepot')}
                id="dossier-autre-dateDepot"
                name="dateDepot"
                data-cy="dateDepot"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.dateEnvoi')}
                id="dossier-autre-dateEnvoi"
                name="dateEnvoi"
                data-cy="dateEnvoi"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.typeDemendeur')}
                id="dossier-autre-typeDemendeur"
                name="typeDemendeur"
                data-cy="typeDemendeur"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.profession')}
                id="dossier-autre-profession"
                name="profession"
                data-cy="profession"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.nature')}
                id="dossier-autre-nature"
                name="nature"
                data-cy="nature"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.statut')}
                id="dossier-autre-statut"
                name="statut"
                data-cy="statut"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.dateDerniereModif')}
                id="dossier-autre-dateDerniereModif"
                name="dateDerniereModif"
                data-cy="dateDerniereModif"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.commentaire')}
                id="dossier-autre-commentaire"
                name="commentaire"
                data-cy="commentaire"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.nom')}
                id="dossier-autre-nom"
                name="nom"
                data-cy="nom"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.prenom')}
                id="dossier-autre-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.telephone')}
                id="dossier-autre-telephone"
                name="telephone"
                data-cy="telephone"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.cIN')}
                id="dossier-autre-cIN"
                name="cIN"
                data-cy="cIN"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.raisonSocial')}
                id="dossier-autre-raisonSocial"
                name="raisonSocial"
                data-cy="raisonSocial"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.iCE')}
                id="dossier-autre-iCE"
                name="iCE"
                data-cy="iCE"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.numRC')}
                id="dossier-autre-numRC"
                name="numRC"
                data-cy="numRC"
                type="text"
              />
              <ValidatedField label={translate('drugStoreApp.dossierAutre.iF')} id="dossier-autre-iF" name="iF" data-cy="iF" type="text" />
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.numAffiliation')}
                id="dossier-autre-numAffiliation"
                name="numAffiliation"
                data-cy="numAffiliation"
                type="text"
              />
              <ValidatedField
                id="dossier-autre-user"
                name="user"
                data-cy="user"
                label={translate('drugStoreApp.dossierAutre.user')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('drugStoreApp.dossierAutre.documentList')}
                id="dossier-autre-documentList"
                data-cy="documentList"
                type="select"
                multiple
                name="documentLists"
              >
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="dossier-autre-utilisateur"
                name="utilisateur"
                data-cy="utilisateur"
                label={translate('drugStoreApp.dossierAutre.utilisateur')}
                type="select"
              >
                <option value="" key="0" />
                {utilisateurs
                  ? utilisateurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="dossier-autre-local"
                name="local"
                data-cy="local"
                label={translate('drugStoreApp.dossierAutre.local')}
                type="select"
              >
                <option value="" key="0" />
                {locals
                  ? locals.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dossier-autre" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DossierAutreUpdate;
