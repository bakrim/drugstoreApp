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
import { IDossierPharmacie } from 'app/shared/model/dossier-pharmacie.model';
import { getEntity, updateEntity, createEntity, reset } from './dossier-pharmacie.reducer';

export const DossierPharmacieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const documents = useAppSelector(state => state.document.entities);
  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const locals = useAppSelector(state => state.local.entities);
  const dossierPharmacieEntity = useAppSelector(state => state.dossierPharmacie.entity);
  const loading = useAppSelector(state => state.dossierPharmacie.loading);
  const updating = useAppSelector(state => state.dossierPharmacie.updating);
  const updateSuccess = useAppSelector(state => state.dossierPharmacie.updateSuccess);

  const handleClose = () => {
    navigate('/dossier-pharmacie');
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
    values.dateDerniereModif = convertDateTimeToServer(values.dateDerniereModif);

    const entity = {
      ...dossierPharmacieEntity,
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
          dateDerniereModif: displayDefaultDateTime(),
        }
      : {
          ...dossierPharmacieEntity,
          dateDepot: convertDateTimeFromServer(dossierPharmacieEntity.dateDepot),
          dateDerniereModif: convertDateTimeFromServer(dossierPharmacieEntity.dateDerniereModif),
          user: dossierPharmacieEntity?.user?.id,
          documentLists: dossierPharmacieEntity?.documentLists?.map(e => e.id.toString()),
          utilisateur: dossierPharmacieEntity?.utilisateur?.id,
          local: dossierPharmacieEntity?.local?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.dossierPharmacie.home.createOrEditLabel" data-cy="DossierPharmacieCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.dossierPharmacie.home.createOrEditLabel">Create or edit a DossierPharmacie</Translate>
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
                  id="dossier-pharmacie-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.numero')}
                id="dossier-pharmacie-numero"
                name="numero"
                data-cy="numero"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.dateDepot')}
                id="dossier-pharmacie-dateDepot"
                name="dateDepot"
                data-cy="dateDepot"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.nature')}
                id="dossier-pharmacie-nature"
                name="nature"
                data-cy="nature"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.statut')}
                id="dossier-pharmacie-statut"
                name="statut"
                data-cy="statut"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.dateDerniereModif')}
                id="dossier-pharmacie-dateDerniereModif"
                name="dateDerniereModif"
                data-cy="dateDerniereModif"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.commentaire')}
                id="dossier-pharmacie-commentaire"
                name="commentaire"
                data-cy="commentaire"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.nom')}
                id="dossier-pharmacie-nom"
                name="nom"
                data-cy="nom"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.prenom')}
                id="dossier-pharmacie-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.telephone')}
                id="dossier-pharmacie-telephone"
                name="telephone"
                data-cy="telephone"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.dossierPharmacie.cIN')}
                id="dossier-pharmacie-cIN"
                name="cIN"
                data-cy="cIN"
                type="text"
              />
              <ValidatedField
                id="dossier-pharmacie-user"
                name="user"
                data-cy="user"
                label={translate('drugStoreApp.dossierPharmacie.user')}
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
                label={translate('drugStoreApp.dossierPharmacie.documentList')}
                id="dossier-pharmacie-documentList"
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
                id="dossier-pharmacie-utilisateur"
                name="utilisateur"
                data-cy="utilisateur"
                label={translate('drugStoreApp.dossierPharmacie.utilisateur')}
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
                id="dossier-pharmacie-local"
                name="local"
                data-cy="local"
                label={translate('drugStoreApp.dossierPharmacie.local')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dossier-pharmacie" replace color="info">
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

export default DossierPharmacieUpdate;
