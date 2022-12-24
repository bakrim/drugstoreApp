import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRole } from 'app/shared/model/role.model';
import { getEntities as getRoles } from 'app/entities/role/role.reducer';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntity, updateEntity, createEntity, reset } from './utilisateur.reducer';

export const UtilisateurUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const roles = useAppSelector(state => state.role.entities);
  const utilisateurEntity = useAppSelector(state => state.utilisateur.entity);
  const loading = useAppSelector(state => state.utilisateur.loading);
  const updating = useAppSelector(state => state.utilisateur.updating);
  const updateSuccess = useAppSelector(state => state.utilisateur.updateSuccess);

  const handleClose = () => {
    navigate('/utilisateur');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getRoles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...utilisateurEntity,
      ...values,
      roleLists: mapIdList(values.roleLists),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...utilisateurEntity,
          roleLists: utilisateurEntity?.roleLists?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.utilisateur.home.createOrEditLabel" data-cy="UtilisateurCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.utilisateur.home.createOrEditLabel">Create or edit a Utilisateur</Translate>
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
                  id="utilisateur-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.utilisateur.email')}
                id="utilisateur-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.utilisateur.username')}
                id="utilisateur-username"
                name="username"
                data-cy="username"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.utilisateur.password')}
                id="utilisateur-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.utilisateur.active')}
                id="utilisateur-active"
                name="active"
                data-cy="active"
                check
                type="checkbox"
              />
              <ValidatedField label={translate('drugStoreApp.utilisateur.nom')} id="utilisateur-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField
                label={translate('drugStoreApp.utilisateur.prenom')}
                id="utilisateur-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.utilisateur.telephone')}
                id="utilisateur-telephone"
                name="telephone"
                data-cy="telephone"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.utilisateur.roleList')}
                id="utilisateur-roleList"
                data-cy="roleList"
                type="select"
                multiple
                name="roleLists"
              >
                <option value="" key="0" />
                {roles
                  ? roles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/utilisateur" replace color="info">
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

export default UtilisateurUpdate;
