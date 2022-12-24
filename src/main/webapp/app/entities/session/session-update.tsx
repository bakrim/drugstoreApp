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
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { getEntities as getUtilisateurs } from 'app/entities/utilisateur/utilisateur.reducer';
import { ISession } from 'app/shared/model/session.model';
import { getEntity, updateEntity, createEntity, reset } from './session.reducer';

export const SessionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const utilisateurs = useAppSelector(state => state.utilisateur.entities);
  const sessionEntity = useAppSelector(state => state.session.entity);
  const loading = useAppSelector(state => state.session.loading);
  const updating = useAppSelector(state => state.session.updating);
  const updateSuccess = useAppSelector(state => state.session.updateSuccess);

  const handleClose = () => {
    navigate('/session');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getUtilisateurs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateConnect = convertDateTimeToServer(values.dateConnect);
    values.dateDeconnect = convertDateTimeToServer(values.dateDeconnect);

    const entity = {
      ...sessionEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      utilisateur: utilisateurs.find(it => it.id.toString() === values.utilisateur.toString()),
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
          dateConnect: displayDefaultDateTime(),
          dateDeconnect: displayDefaultDateTime(),
        }
      : {
          ...sessionEntity,
          dateConnect: convertDateTimeFromServer(sessionEntity.dateConnect),
          dateDeconnect: convertDateTimeFromServer(sessionEntity.dateDeconnect),
          user: sessionEntity?.user?.id,
          utilisateur: sessionEntity?.utilisateur?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="drugStoreApp.session.home.createOrEditLabel" data-cy="SessionCreateUpdateHeading">
            <Translate contentKey="drugStoreApp.session.home.createOrEditLabel">Create or edit a Session</Translate>
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
                  id="session-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('drugStoreApp.session.adresseIp')}
                id="session-adresseIp"
                name="adresseIp"
                data-cy="adresseIp"
                type="text"
              />
              <ValidatedField
                label={translate('drugStoreApp.session.dateConnect')}
                id="session-dateConnect"
                name="dateConnect"
                data-cy="dateConnect"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('drugStoreApp.session.dateDeconnect')}
                id="session-dateDeconnect"
                name="dateDeconnect"
                data-cy="dateDeconnect"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField id="session-user" name="user" data-cy="user" label={translate('drugStoreApp.session.user')} type="select">
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
                id="session-utilisateur"
                name="utilisateur"
                data-cy="utilisateur"
                label={translate('drugStoreApp.session.utilisateur')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/session" replace color="info">
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

export default SessionUpdate;
